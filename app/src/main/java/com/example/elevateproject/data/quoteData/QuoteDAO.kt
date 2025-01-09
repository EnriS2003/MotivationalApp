package com.example.elevateproject.data.quoteData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuoteDao {
    @Query("SELECT * FROM quote_table WHERE date = :date LIMIT 1")
    fun getQuoteByDate(date: String): QuoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuote(quote: QuoteEntity)
}
