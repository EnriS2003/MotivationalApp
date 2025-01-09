package com.example.elevateproject.data.quoteData


/*
Repository for managing quotes.
 */
class QuoteRepository(private val quoteDao: QuoteDao) {

    fun getQuoteByDate(date: String): QuoteEntity? {
        return quoteDao.getQuoteByDate(date)
    }

    suspend fun insertQuote(quote: QuoteEntity) {
        quoteDao.insertQuote(quote)
    }
}
