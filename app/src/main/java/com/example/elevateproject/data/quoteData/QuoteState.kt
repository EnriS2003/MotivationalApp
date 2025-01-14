package com.example.elevateproject.data.quoteData

/**
 * Represents the state of the UI for displaying quotes.
 *
 * This data class is used to manage and represent the current state of the quote-related
 * UI components. It provides the necessary fields to handle loading, errors, and the
 * actual quote and author information.
 */
data class QuoteState(
    val quote: String = "",
    val author: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)