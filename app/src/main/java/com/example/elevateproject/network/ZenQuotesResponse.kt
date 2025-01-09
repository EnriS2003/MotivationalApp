package com.example.elevateproject.network

/*
Data class representing a quote response from the API.
 */

data class ZenQuotesResponse(
    val q: String,       // Text of the quote
    val a: String,       // Name of the author
)