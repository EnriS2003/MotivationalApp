package com.example.elevateproject.data.quoteData

/*
Maintains the state of the UI for the quote.
 */
data class QuoteState(
    val quote: String = "",
    val author: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)