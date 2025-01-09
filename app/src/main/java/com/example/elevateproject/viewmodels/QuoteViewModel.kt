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
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/*
ViewModel for managing the state of the quote.
 */
class QuoteViewModel(private val repository: QuoteRepository) : ViewModel() {

    private val _quoteState = MutableStateFlow(QuoteState())
    var quoteState: StateFlow<QuoteState> = _quoteState

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite

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

                // Check if the quote is already saved
                _isFavorite.value = repository.isQuoteSaved(quote.q, quote.a)
            } catch (e: Exception) {
                _quoteState.value = QuoteState(error = e.message ?: "Unknown error")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveQuoteToFavorites(quote: String, author: String) {
        viewModelScope.launch {
            repository.saveQuote(quote, author)
            _isFavorite.value = true
        }
    }

    fun removeQuoteFromFavorites(quote: String, author: String) {
        viewModelScope.launch {
            repository.deleteQuote(quote, author)
            _isFavorite.value = false
        }
    }


}
