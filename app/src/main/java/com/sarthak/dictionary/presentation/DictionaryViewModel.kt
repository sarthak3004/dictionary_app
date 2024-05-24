package com.sarthak.dictionary.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarthak.dictionary.data.local.Word
import com.sarthak.dictionary.domain.model.WordItem
import com.sarthak.dictionary.domain.repository.DictionaryRepository
import com.sarthak.dictionary.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
  private val dictionaryRepository: DictionaryRepository
): ViewModel() {
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _isTextFieldFocused = mutableStateOf(false)
    val isTextFieldFocused: State<Boolean> = _isTextFieldFocused

    private val _state = mutableStateOf(DictionaryState())
    val state: State<DictionaryState> = _state

    private var searchJob: Job? = null

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                wordHistory = dictionaryRepository.getSearchHistory("%%").filterNotNull().first()
            )
        }
    }

    fun updateTextFieldFocus(isTextFieldFocused: Boolean) {
        _isTextFieldFocused.value = isTextFieldFocused
    }
    fun updateSearchQuery(newQuery: String) {
        _searchQuery.value = newQuery
        _state.value = _state.value.copy(
            isLoading = false,
            isError = false,
            errorMessage = "",
            wordItems = emptyList(),
        )
        viewModelScope.launch {
            _state.value = _state.value.copy(
                wordHistory = dictionaryRepository.getSearchHistory("%${_searchQuery.value}%").filterNotNull().first()
            )
        }
    }

    fun removeWordAndUpdateDB(word: String) {
        viewModelScope.launch {
            dictionaryRepository.removeWord(word)
            _state.value = _state.value.copy(
                wordHistory = dictionaryRepository.getSearchHistory("%${_searchQuery.value}%").filterNotNull().first()
            )
        }
    }

    fun onSearchClicked() {
        _state.value = _state.value.copy(
            currentWord = _searchQuery.value,
        )
        viewModelScope.launch {
            dictionaryRepository.removeWord((_state.value.currentWord).lowercase())
            dictionaryRepository.addWord(Word(0, (state.value.currentWord).lowercase(), System.currentTimeMillis()))
        }
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            dictionaryRepository.getWordResult(_state.value.currentWord).collect{ result ->
                when(result) {
                    is Result.Error -> {
                        _state.value = _state.value.copy(
                            isError = true,
                            isLoading = false,
                            errorMessage = result.message,
                            wordItems = emptyList()
                        )
                    }
                    is Result.Loading -> {
                        _state.value = _state.value.copy(
                            isError = false,
                            isLoading = true,
                            errorMessage = "",
                            wordItems = emptyList()
                        )
                    }
                    is Result.Success -> {
                        result.data?.let {
                            _state.value = _state.value.copy(
                                isError = false,
                                isLoading = false,
                                errorMessage = "",
                                wordItems = it
                            )
                        }

                    }
                }
            }
        }
    }
}

data class DictionaryState(
    var currentWord: String = "",
    val wordItems: List<WordItem> = emptyList(),
    var isLoading: Boolean = false,
    var isError: Boolean = false,
    var errorMessage: String = "",
    var wordHistory: List<String> = emptyList(),
)
