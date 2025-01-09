package com.example.elevateproject.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.elevateproject.data.quoteData.QuoteState
import com.example.elevateproject.ui.theme.ElevateProjectTheme
import com.example.elevateproject.viewmodels.QuoteViewModel
import kotlinx.coroutines.flow.MutableStateFlow

/*
Main entry point for the app. Here is defined the home screen in a Scaffold. The bottom bar
is attached to the Scaffold.
 */
@Composable
fun HomeScreen(viewModel: QuoteViewModel = viewModel(), navController: NavController) {
    val quoteState by viewModel.quoteState.collectAsState()

    // Fetch the quote when the screen is displayed
    LaunchedEffect(Unit) {
        viewModel.fetchRandomQuote()
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        content = { padding -> //Padding for organizing the content on the Scaffold
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Quote",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(8.dp)
                )
                // Show loading, error, or quote
                when {
                    quoteState.isLoading -> {
                        CircularProgressIndicator()
                    }
                    quoteState.error != null -> {
                        Text(
                            text = "Error: ${quoteState.error}",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    else -> {
                        Text(
                            text = quoteState.quote,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                        Text(
                            text = "— ${quoteState.author}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "Save to Favorites",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    // Simulates a ViewModel with a quote
    val quoteViewModel = remember {
        QuoteViewModel().apply {
            quoteState = MutableStateFlow(
                QuoteState(
                    quote = "To be or not to be, that is the question.",
                    author = "William Shakespeare",
                    isLoading = false
                )
            )
        }
    }

    // Simulates the NavController
    val navController = rememberNavController()

    // Applies the theme to the preview
    ElevateProjectTheme {
        HomeScreen(viewModel = quoteViewModel, navController = navController)
    }
}