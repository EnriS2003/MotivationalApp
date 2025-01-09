package com.example.elevateproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elevateproject.data.quoteData.QuoteState
import com.example.elevateproject.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/*
ViewModel for managing the state of the quote.
 */
class QuoteViewModel(
    //private val repository: QuoteRepository
) : ViewModel() {

    private val _quoteState = MutableStateFlow(QuoteState())
    var quoteState: StateFlow<QuoteState> = _quoteState

    private var isQuoteLoaded = false

    // Function for fetching a random quote.
    fun fetchRandomQuote() {
        if (isQuoteLoaded) return

        _quoteState.value = QuoteState(isLoading = true)
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getQuotes()
                val quote = response[0] //The answer is an array of quotes
                _quoteState.value = QuoteState(
                    quote = quote.q,
                    author = quote.a
                )
                isQuoteLoaded = true // Set the flag to true after loading the quote
            } catch (e: Exception) {
                _quoteState.value = QuoteState(error = e.message ?: "Unknown error")
            }
        }
    }

    // Function for fetching the daily quote
//    fun fetchDailyQuote() {
//        viewModelScope.launch {
//            val today = dateFormatter.format(Date()) // Obtain today's date in the desired format
//            val cachedQuote = repository.getQuoteByDate(today) // Check if there is a quote saved for today
//
//            if (cachedQuote != null) {
//                // Use the cached quote
//                _quoteState.value = QuoteState(
//                    quote = cachedQuote.quote,
//                    author = cachedQuote.author
//                )
//            } else {
//                // Request a new quote from the API and save it
//                _quoteState.value = QuoteState(isLoading = true)
//                try {
//                    val response = RetrofitInstance.api.getQuotes()
//                    val quote = response[0]
//
//                    // Save the quote to the database
//                    repository.insertQuote(
//                        QuoteEntity(
//                            quote = quote.q,
//                            author = quote.a,
//                            date = today
//                        )
//                    )
//
//                    _quoteState.value = QuoteState(
//                        quote = quote.q,
//                        author = quote.a
//                    )
//
//                } catch (e: Exception) {
//                    _quoteState.value = QuoteState(
//                        error = e.message ?: "Unknown error"
//                    )
//                }
//            }
//        }
//    }

}
