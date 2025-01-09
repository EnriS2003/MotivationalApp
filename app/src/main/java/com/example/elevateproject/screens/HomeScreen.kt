package com.example.elevateproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.elevateproject.viewmodels.QuoteViewModel

/*
Main entry point for the app. Here is defined the home screen in a Scaffold. The bottom bar
is attached to the Scaffold.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: QuoteViewModel = viewModel(), navController: NavController) {
    val quoteState by viewModel.quoteState.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    // Fetch the quote when the screen is displayed
    LaunchedEffect(Unit) {
        viewModel.fetchRandomQuote()
    }

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("favorites") }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Go to Favorites",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
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
                                if (isFavorite) {
                                    viewModel.removeQuoteFromFavorites(quoteState.quote, quoteState.author)
                                } else {
                                    viewModel.saveQuoteToFavorites(quoteState.quote, quoteState.author)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from Favorites" else "Save to Favorites",
                                tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, viewModel: QuoteViewModel) {
    val favoriteQuotes by viewModel.favoriteQuotes.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorite Quotes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Favorite, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (favoriteQuotes.isEmpty()) {
                Text(
                    text = "No favorite quotes yet!",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight()
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(favoriteQuotes) { quote ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surface)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = quote.quote,
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                Text(
                                    text = "— ${quote.author}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
