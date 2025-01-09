package com.example.elevateproject.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/*
Retrofit instance for making API requests.
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