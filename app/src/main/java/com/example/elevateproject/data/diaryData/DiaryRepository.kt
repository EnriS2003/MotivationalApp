package com.example.elevateproject.data.diaryData

/**
 * Repository class for managing diary-related data operations.
 *
 * Responsibilities:
 * 1. Acts as a single source of truth for accessing and manipulating diary data.
 * 2. Provides an abstraction over the `DiaryDao`, enabling the ViewModel to interact with the data layer.
 * 3. Handles CRUD operations (Create, Read, Update, Delete) for diary entries.
 * 4. Facilitates the use of Flows to provide reactive and asynchronous data streams to the UI layer.
 *
 * This repository ensures that all data operations adhere to the principles of separation of concerns,
 * keeping database logic isolated from the ViewModel and UI layers.
 */
class DiaryRepository(private val diaryDao: DiaryDao) {

    // Fetch all diaries as a Flow
    fun getAllDiaries() = diaryDao.getAllDiaries()

    // Add a new diary
    suspend fun addDiary(diary: DiaryEntity) {
        diaryDao.insertDiary(diary)
    }

    // Remove a diary
    suspend fun removeDiary(diary: DiaryEntity) {
        diaryDao.deleteDiary(diary)
    }

    // Get a specific diary by its ID
    fun getDiaryById(id: String) = diaryDao.getDiaryById(id)

    // Updates diary information
    suspend fun updateDiary(diary: DiaryEntity) {
        diaryDao.updateDiary(diary)
    }

}

