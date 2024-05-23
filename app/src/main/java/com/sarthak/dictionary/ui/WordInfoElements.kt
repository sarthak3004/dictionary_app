package com.sarthak.dictionary.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.sarthak.dictionary.domain.model.Definition
import com.sarthak.dictionary.domain.model.Meaning
import com.sarthak.dictionary.domain.model.WordItem

@Composable
fun DefinitionElement(definition: Definition) {
    Column {
        Text(
            text = definition.definition,
            style = MaterialTheme.typography.bodyMedium
        )
        if(definition.example.isNotEmpty()) {
            Text(
                text = "\"${definition.example}\"",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun MeaningElement(meaning: Meaning) {
    Column {
        Text(
            text = meaning.partOfSpeech,
            fontStyle = FontStyle.Italic
        )
        meaning.definitions.forEachIndexed { index, definition ->
            Row(modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp)) {
                Text(text = "${index + 1}. ")
                DefinitionElement(
                    definition = definition
                )
            }
        }
    }
}

@Composable
fun WordElement(word: WordItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Text(
                text = word.word,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1.0F)
            )
            Spacer(modifier = Modifier.width(32.dp))
            Text(
                text = word.phonetic,
                style = MaterialTheme.typography.bodyMedium,
                fontStyle = FontStyle.Italic,

            )
        }
        word.meanings.forEach {meaning ->
            MeaningElement(meaning = meaning)
        }
    }
}

@Composable
fun WordResultScreen(wordItems: List<WordItem>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(8.dp)
    ) {
        items(wordItems) {wordItem ->
            Card(modifier = Modifier.padding(bottom = 8.dp)) {
                WordElement(word = wordItem, Modifier.padding(8.dp))
            }
        }
    }
}
