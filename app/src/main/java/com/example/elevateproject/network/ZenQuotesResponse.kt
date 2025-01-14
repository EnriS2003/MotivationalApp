package com.example.elevateproject.network

/**
 * Represents the response model for Zen Quotes API.
 *
 * This data class is used to parse the JSON response received from the Zen Quotes API.
 * It includes the fields:
 * - `q`: The text of the quote.
 * - `a`: The author of the quote.
 *
 * Example JSON:
 * ```
 * {
 *   "q": "Your time is limited, so don’t waste it living someone else’s life.",
 *   "a": "Steve Jobs"
 * }
 * ```
 */
data class ZenQuotesResponse(
    val q: String,       // Text of the quote
    val a: String,       // Name of the author
)