package com.sarthak.dictionary.domain.model

data class WordItem(
    val meanings: List<Meaning>,
    val word: String,
    val phonetic: String
)