package com.example.elevateproject.data.quoteData

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/*
Repository for managing quotes.
 */
class QuoteRepository(private val quoteDao: QuoteDao) {

    // To insert a quote into the database
    suspend fun insertQuote(quote: QuoteEntity) {
        quoteDao.insertQuote(quote)
    }

    fun getAllFavoriteQuotes(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllQuotes()
    }

    // Eliminates a quote from the database
    suspend fun deleteQuote(quote: String, author: String) {
        val quoteEntity = QuoteEntity(
            quote = quote,
            author = author,
            date = ""
        )
        quoteDao.deleteQuote(quoteEntity)
    }

    // Verifies if a quote is saved in the database
    suspend fun isQuoteSaved(quote: String, author: String): Boolean {
        return quoteDao.getQuote(quote, author) != null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun saveQuote(quote: String, author: String) {
        val currentDate = LocalDateTime.now().toString()
        val quoteEntity = QuoteEntity(
            quote = quote,
            author = author,
            date = currentDate
        )
        quoteDao.insertQuote(quoteEntity)
    }
}

