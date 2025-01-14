package com.example.elevateproject.data.quoteData

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

/**
 * Repository for managing quotes.
 *
 * The `QuoteRepository` serves as an abstraction layer between the `QuoteDao`
 * (data access object) and the rest of the application. It provides methods for
 * interacting with the quotes in the database, such as saving, deleting, and fetching
 * quotes. This layer is crucial for maintaining clean architecture and ensuring
 * separation of concerns.
 *
 * Methods:
 * - `getAllFavoriteQuotes`: Fetches all saved quotes from the database as a `Flow`.
 * - `deleteQuote`: Removes a quote from the database based on its text and author.
 * - `isQuoteSaved`: Checks whether a specific quote exists in the database.
 * - `saveQuote`: Saves a new quote to the database with the current timestamp.
 *
 * Dependencies:
 * This class relies on the `QuoteDao` for performing database operations. It is
 * injected into `QuoteViewModel` class for use in the UI layer.
 */
class QuoteRepository(private val quoteDao: QuoteDao) {

    // Retrieves all favorite quotes from the database
    fun getAllFavoriteQuotes(): Flow<List<QuoteEntity>> {
        return quoteDao.getAllQuotes()
    }

    // Eliminates a quote from the database
    suspend fun deleteQuote(quote: String, author: String) {
        quoteDao.deleteQuote(quote, author)
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



