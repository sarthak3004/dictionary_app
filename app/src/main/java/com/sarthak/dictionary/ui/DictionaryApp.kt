package com.sarthak.dictionary.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarthak.dictionary.R

@Composable
fun DictionaryApp() {
    Scaffold(
        topBar = { DictionaryTopAppBar(title = stringResource(id = R.string.app_name)) }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            val viewModel: DictionaryViewModel = hiltViewModel()
            val dictionaryState = viewModel.state
            WordSearchBar(
                searchQuery = viewModel.searchQuery.value,
                onSearchQueryChange = { it -> viewModel.updateSearchQuery(it) },
                onSearchClick = { viewModel.onSearchClicked() },
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
fun DictionaryTopAppBar(title: String, modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall
        ) },
//        colors = TopAppBarDefaults.topAppBarColors(
//            containerColor = Color(0xFF3B5B7E),
//            titleContentColor = Color.White,
//        ),
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewScreen() {

}