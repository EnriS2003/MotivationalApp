package com.example.elevateproject.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elevateproject.data.quoteData.QuoteEntity
import com.example.elevateproject.data.quoteData.QuoteRepository
import com.example.elevateproject.data.quoteData.QuoteState
import com.example.elevateproject.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel for managing the state and interactions with quotes.
 *
 * This `QuoteViewModel` acts as the bridge between the UI layer and the `QuoteRepository`,
 * handling business logic and maintaining the state for quotes. It interacts with both
 * the network API and the local database to provide functionality such as fetching random
 * quotes, saving quotes to favorites, and managing the user's favorite quotes.
 *
 * Properties:
 * - `_quoteState`: Internal state for holding the current quote and its status (loading, error, or data).
 * - `quoteState`: Publicly exposed `StateFlow` for observing the current quote and its state.
 * - `_isFavorite`: Internal state to indicate whether the current quote is marked as favorite.
 * - `isFavorite`: Publicly exposed `StateFlow` for observing the favorite status of the current quote.
 * - `favoriteQuotes`: A `StateFlow` providing a list of all favorite quotes from the database.
 *
 * Methods:
 * - `fetchRandomQuote`: Fetches a random quote from the network API and updates the state.
 *   It also checks if the fetched quote is already saved in the favorites.
 * - `saveQuoteToFavorites`: Saves the current quote to the database and marks it as favorite.
 * - `removeQuoteFromFavorites`: Removes a quote from the database and updates the favorite status.
 * - `checkIfQuoteIsSaved`: Asynchronously checks if a specific quote is saved in the database,
 *   and provides the result via a callback.
 *
 * Dependencies:
 * This ViewModel relies on:
 * - `QuoteRepository` for interacting with the local database and performing quote-related operations.
 * - `RetrofitInstance` for fetching quotes from a remote API.
 *
 * Lifecycle:
 * - The ViewModel uses `viewModelScope` to launch coroutines for asynchronous operations,
 *   ensuring that all tasks are properly canceled when the ViewModel is cleared.
 */
class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {

    private val _quoteState = MutableStateFlow(QuoteState())
    var quoteState: StateFlow<QuoteState> = _quoteState

    val favoriteQuotes: StateFlow<List<QuoteEntity>> = repository.getAllFavoriteQuotes()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    // Fetch random quote
    fun fetchRandomQuote() {
        _quoteState.value = QuoteState(isLoading = true)

        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getQuotes()
                val quote = response[0]
                _quoteState.value = QuoteState(quote = quote.q, author = quote.a)

            } catch (e: Exception) {
                _quoteState.value = QuoteState(error = e.message ?: "Unknown error")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveQuoteToFavorites(quote: String, author: String) {
        viewModelScope.launch {
            repository.saveQuote(quote, author)
        }
    }

    fun removeQuoteFromFavorites(quote: String, author: String) {
        viewModelScope.launch {
                repository.deleteQuote(quote, author)
        }
    }

    fun checkIfQuoteIsSaved(quote: String, author: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isSaved = repository.isQuoteSaved(quote, author)
            onResult(isSaved)
        }
    }

    fun getRandomSavedQuote(onResult: (QuoteEntity?) -> Unit) {
        viewModelScope.launch {
            val quotes = repository.getAllFavoriteQuotes().firstOrNull()
            val randomQuote = quotes?.shuffled()?.firstOrNull()
            onResult(randomQuote)
        }
    }


}
