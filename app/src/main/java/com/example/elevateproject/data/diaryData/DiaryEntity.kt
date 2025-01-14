package com.example.elevateproject.data.diaryData

import androidx.room.Entity
import androidx.room.PrimaryKey

/*
 * Represents a diary entry in the database.
 */
@Entity(tableName = "diary_table")
data class DiaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Unique ID for each diary entry
    val title: String,      // Title
    val content: String,    // Content
    val date: String        // Creation date
)
