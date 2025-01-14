package com.example.elevateproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Provides the singleton instance of the Retrofit client.
 *
 * This object is used to set up the Retrofit client for making HTTP requests.
 * It includes the base URL of the API and the configuration for the HTTP client.
 *
 * Features:
 * - Uses the `ZenQuotesApi` interface to define the endpoints.
 * - Configured with `GsonConverterFactory` for parsing JSON responses.
 */
object RetrofitInstance {
    private const val BASE_URL = "https://zenquotes.io/api/"

    val api: ZenQuotesApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ZenQuotesApi::class.java)
    }
}