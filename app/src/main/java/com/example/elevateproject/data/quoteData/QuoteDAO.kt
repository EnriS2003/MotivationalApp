package com.example.elevateproject.data.quoteData

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes_table")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Delete
    suspend fun deleteQuote(quote: QuoteEntity)

    @Query("SELECT * FROM quotes_table WHERE quote = :quote AND author = :author LIMIT 1")
    suspend fun getQuote(quote: String, author: String): QuoteEntity?
}
