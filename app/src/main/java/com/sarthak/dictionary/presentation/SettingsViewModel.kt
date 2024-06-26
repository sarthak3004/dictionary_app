package com.sarthak.dictionary.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarthak.dictionary.data.preferences.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
): ViewModel() {
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode

    init {
        viewModelScope.launch {
            userPreferencesRepository.isDarkMode.collect { isDarkMode ->
                _isDarkMode.value = isDarkMode
            }
        }
    }

    fun toggleDarkMode(isDarkMode: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveDarkModeSetting(isDarkMode)
        }
    }

}