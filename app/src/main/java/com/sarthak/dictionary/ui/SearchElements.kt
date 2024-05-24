package com.sarthak.dictionary.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sarthak.dictionary.R

@Composable
fun WordSearchBar(
    searchQuery: String,
    onSearchQueryChange:(String) -> Unit,
    onSearchClick:() -> Unit,
    onFocusChange:(Boolean) -> Unit,
    localFocusManager: FocusManager,
    modifier: Modifier = Modifier
) {

    TextField(
        value = searchQuery,
        onValueChange = { onSearchQueryChange(it) },
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.search_bar_placeholder),
                style = MaterialTheme.typography.bodySmall,
                fontSize = 16.sp
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "searchIcon"
            )
        },
        trailingIcon = {
            if(searchQuery.isNotEmpty()) {
                IconButton(onClick = { onSearchQueryChange("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "clearIcon"
                    )
                }
            }
        },
        modifier = modifier.fillMaxWidth().onFocusChanged { focusState ->
            onFocusChange(focusState.isFocused)
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = RoundedCornerShape(24.dp),
        keyboardOptions = KeyboardOptions(
            autoCorrect = true,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if(searchQuery.isNotEmpty()) {
                    onSearchClick()
                    localFocusManager.clearFocus()
                }
            }
        )
    )
}

@Composable
fun SearchSuggestionItem(
    word: String,
    onCrossClick:(String) -> Unit,
    onWordClick: (String) -> Unit,
    localFocusManager: FocusManager,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .height(28.dp)
            .padding(horizontal = 4.dp)
            .clickable {
                onWordClick(word)
                localFocusManager.clearFocus()
            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_history_24),
            contentDescription = "History Icon",
            modifier = Modifier
                .padding(4.dp)
                .size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = word,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(vertical = 4.dp)
                .weight(1.0F)
        )
        IconButton(onClick = { onCrossClick(word) }) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "Clear Icon",
                Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun SearchSuggestionList(
    suggestionsList: List<String>,
    onCrossClick:(String) -> Unit,
    onWordClick: (String) -> Unit,
    localFocusManager: FocusManager,
    modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxWidth().padding(8.dp)) {
        items(suggestionsList) {suggestion ->
            SearchSuggestionItem(word = suggestion, onCrossClick, onWordClick, localFocusManager, modifier = Modifier)
            HorizontalDivider(thickness = 1.dp)
        }
    }
}

