package com.sarthak.dictionary.domain.repository

import com.sarthak.dictionary.domain.model.WordItem
import kotlinx.coroutines.flow.Flow
import com.sarthak.dictionary.util.Result

interface DictionaryRepository {
    fun getWordResult(wordQuery: String): Flow<Result<List<WordItem>>>
}