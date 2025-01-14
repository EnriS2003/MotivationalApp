package com.example.elevateproject.network

import retrofit2.http.GET

/**
 * Defines the API endpoints for Zen Quotes.
 *
 * This interface contains methods that correspond to specific HTTP endpoints for
 * retrieving quotes. Each method is annotated with Retrofit annotations like `@GET`
 * to specify the request type and endpoint.
 *
 * Endpoints:
 * - `getQuotes()`: Fetches a list of random quotes from the API.
 */
interface ZenQuotesApi {

    @GET("quotes/")
    suspend fun getQuotes(): List<ZenQuotesResponse>
}