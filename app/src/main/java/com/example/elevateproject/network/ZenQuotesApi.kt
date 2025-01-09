package com.example.elevateproject.network

import retrofit2.http.GET
import retrofit2.http.Query

interface ZenQuotesApi {

    @GET("quotes/")
    suspend fun getQuotes(): List<ZenQuotesResponse>

    // Obtain a random quote
    @GET("random")
    suspend fun getRandomQuote(): List<ZenQuotesResponse>

    // Obtain the quote of the day
    @GET("today")
    suspend fun getQuoteOfTheDay(): List<ZenQuotesResponse>


    // Obtain a quote by keyword
    @GET("quotes/")
    suspend fun getQuotesByKeyword(
        @Query("keyword") keyword: String
    ): List<ZenQuotesResponse>
}