package com.example.elevateproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.elevateproject.data.quoteData.QuoteEntity
import com.example.elevateproject.viewmodels.QuoteViewModel

/**
 * HomeScreen
 *
 * This composable function represents the main screen of the application where a random quote
 * is fetched and displayed. It handles the following scenarios:
 *
 * 1. Displays a loading indicator while fetching a quote.
 * 2. Displays an error message if the quote fetch fails. In this case, it shows a saved quote
 *    from the database (if available) as a fallback. An icon shows that the quote is one of the favorites
 *    one.
 * 3. Allows the user to save or remove the displayed quote to/from favorites. The favorite
 *    status is visually represented by the icon.
 * 4. Provides a floating action button for navigating to the "Favorites" screen.
 *
 * Key Features:
 * - Dynamically fetches and displays quotes using the `QuoteViewModel`.
 * - Reacts to changes in favorite status with an updated icon.
 * - Supports navigation to other parts of the application.
 * - Automatic change between Navigation Bar and Navigation Rail based on screen size.
 *
 * Parameters:
 * - `viewModel`: The `QuoteViewModel` that manages the state and operations for quotes.
 * - `navController`: The `NavController` for handling navigation between screens.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(viewModel: QuoteViewModel = viewModel(), navController: NavController) {
    val quoteState by viewModel.quoteState.collectAsState()
    var isQuoteSaved by remember { mutableStateOf(false) } // State to verify if the quote is saved

    // Fetch the quote when the screen is displayed
    LaunchedEffect(Unit) {
        viewModel.fetchRandomQuote()
    }
    Scaffold(
        bottomBar = {
            if (!isLandscape()) {
                BottomBar(navController)
            }
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
            Row {
                if (isLandscape()) {
                    NavigationRail(navController)
                }
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
                            // Show a saved citation
                            var savedQuote by remember { mutableStateOf<QuoteEntity?>(null) }

                            LaunchedEffect(Unit) {
                                viewModel.getRandomSavedQuote { randomQuote ->
                                    savedQuote = randomQuote
                                }
                            }

                            if (savedQuote != null) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = savedQuote!!.quote,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                    )
                                    Text(
                                        text = "— ${savedQuote!!.author}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        modifier = Modifier.padding(bottom = 16.dp)
                                    )

                                    IconButton(
                                        onClick = { println("This is a favorite quote")}
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Favorite,
                                            contentDescription = "Show this is a favorite quote" ,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                }

                            } else {
                                Text(
                                    text = "Error: ${quoteState.error}",
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
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
                                    if (isQuoteSaved) {
                                        viewModel.removeQuoteFromFavorites(quoteState.quote, quoteState.author)
                                    } else {
                                        viewModel.saveQuoteToFavorites(quoteState.quote, quoteState.author)
                                    }
                                    isQuoteSaved =!isQuoteSaved
                                }
                            ) {
                                Icon(
                                    imageVector = if (isQuoteSaved) Icons.Default.Favorite else Icons.Filled.FavoriteBorder,
                                    contentDescription = if (isQuoteSaved) "Remove from Favorites" else "Save to Favorites",
                                    tint = if (isQuoteSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}

/**
 * FavoritesScreen
 *
 * This composable function displays a list of quotes that have been marked as favorites by the user.
 * It provides the following functionalities:
 *
 * 1. Fetches and displays through a LazyColumn all favorite quotes stored in the database.
 * 2. Allows users to remove quotes from their favorites. The removal action updates the database
 *    and the UI dynamically.
 * 3. Displays a message if there are no saved favorite quotes.
 * 4. Supports navigation back to the home screen using a top app bar.
 * 5. Automatic change between Navigation Bar and Navigation Rail based on screen size.
 *
 * Parameters:
 * - `navController`: The `NavController` for handling navigation between screens.
 * - `viewModel`: The `QuoteViewModel` that provides access to favorite quotes.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(navController: NavController, viewModel: QuoteViewModel) {
    val favoriteQuotes by viewModel.favoriteQuotes.collectAsState()

    Scaffold(
        bottomBar = {
            if (!isLandscape()) {
                BottomBar(navController)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Favorite Quotes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Row {
            if (isLandscape()) {
                NavigationRail(navController)
            }
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
                                    IconButton(onClick = {
                                        viewModel.removeQuoteFromFavorites(quote.quote, quote.author)
                                        println("Removing quote...")

                                        viewModel.checkIfQuoteIsSaved(quote.quote, quote.author) { isSaved ->
                                            if (isSaved) {
                                                println("Quote still staved")
                                            } else {
                                                println("Quote is not saved")
                                            }
                                        }

                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Remove from Favorites",
                                            tint = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
