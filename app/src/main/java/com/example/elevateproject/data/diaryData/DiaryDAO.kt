package com.example.elevateproject.data.diaryData

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/*
Defines the database operations for diary entries.
 */
@Dao
interface DiaryDao {

    // Fetch all diaries ordered by date
    @Query("SELECT * FROM diary_table ORDER BY date DESC")
    fun getAllDiaries(): Flow<List<DiaryEntity>>

    // Insert a new diary
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDiary(diary: DiaryEntity)

    // Delete a diary
    @Delete
    suspend fun deleteDiary(diary: DiaryEntity)

    // Get a diary by its ID. This returns only one diary.
    @Query("SELECT * FROM diary_table WHERE id = :id LIMIT 1")
    fun getDiaryById(id: String): Flow<DiaryEntity>

    // Update a diary
    @Update
    suspend fun updateDiary(diary: DiaryEntity)

}

