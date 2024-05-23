package com.sarthak.dictionary.data.mapper

import com.sarthak.dictionary.data.dto.DefinitionDto
import com.sarthak.dictionary.data.dto.MeaningDto
import com.sarthak.dictionary.data.dto.WordItemDto
import com.sarthak.dictionary.domain.model.Definition
import com.sarthak.dictionary.domain.model.Meaning
import com.sarthak.dictionary.domain.model.WordItem

fun DefinitionDto.toDefinition() = Definition(
    definition = definition ?: "",
    example = example ?: ""
)

fun MeaningDto.toMeaning() = Meaning(
    definitions = definitions?.map {
        it.toDefinition()
    } ?: emptyList(),
    partOfSpeech = partOfSpeech ?: ""
)

fun WordItemDto.toWordItem() = WordItem(
    meanings = meanings?.map {
        it.toMeaning()
    } ?: emptyList(),
    word = word ?: "",
    phonetic = phonetic ?: ""
)