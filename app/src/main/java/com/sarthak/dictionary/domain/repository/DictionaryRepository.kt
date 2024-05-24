package com.sarthak.dictionary.domain.repository

import com.sarthak.dictionary.data.local.Word
import com.sarthak.dictionary.domain.model.WordItem
import kotlinx.coroutines.flow.Flow
import com.sarthak.dictionary.util.Result

interface DictionaryRepository {
    fun getWordResult(wordQuery: String): Flow<Result<List<WordItem>>>
    suspend fun addWord(word: Word)
    suspend fun removeWord(word: String)
    fun getSearchHistory(currentQuery: String): Flow<List<String>>
}