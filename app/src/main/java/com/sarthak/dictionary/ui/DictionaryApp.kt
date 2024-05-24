package com.sarthak.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarthak.dictionary.R
import com.sarthak.dictionary.presentation.DictionaryViewModel
import com.sarthak.dictionary.presentation.SettingsViewModel
import com.sarthak.dictionary.ui.theme.DictionaryTheme

@Composable
fun DictionaryApp() {
    val settingsViewModel: SettingsViewModel = hiltViewModel()
    val isDarkMode = settingsViewModel.isDarkMode.value
    DictionaryTheme(darkTheme = isDarkMode) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                topBar = { DictionaryTopAppBar(title = stringResource(id = R.string.app_name), isDarkMode = isDarkMode, toggleDarkMode = settingsViewModel::toggleDarkMode) }
            ) { innerPadding ->
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    val dictionaryViewModel: DictionaryViewModel = hiltViewModel()
                    val dictionaryState = dictionaryViewModel.state
                    WordSearchBar(
                        searchQuery = dictionaryViewModel.searchQuery.value,
                        onSearchQueryChange = { it -> dictionaryViewModel.updateSearchQuery(it) },
                        onSearchClick = { dictionaryViewModel.onSearchClicked() },
                        modifier = Modifier.padding(8.dp)
                    )

                    if(dictionaryState.value.isError) {
                        ErrorScreen(dictionaryState.value.errorMessage, modifier = Modifier.align(Alignment.CenterHorizontally))
                    } else if (dictionaryState.value.isLoading) {
                        LoadingScreen()
                    } else {
                        WordResultScreen(dictionaryState.value.wordItems)
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(80.dp),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ErrorScreen(errorMessage: String, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictionaryTopAppBar(title: String,isDarkMode: Boolean, toggleDarkMode: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
        ) },
        actions = {
            IconButton(
                onClick = {
                    toggleDarkMode(!isDarkMode)
                }
            ) {
                Icon(
                    painter = if(isDarkMode) {
                        painterResource(id = R.drawable.baseline_dark_mode_24)
                    } else {
                        painterResource(id = R.drawable.outline_dark_mode_24)
                    },
                    contentDescription = "Dark Mode Icon"
                )
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {

}