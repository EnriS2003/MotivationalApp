package com.example.elevateproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elevateproject.data.diaryData.DiaryEntity
import com.example.elevateproject.data.diaryData.DiaryRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for managing diary-related operations and data.
 *
 * Responsibilities:
 * 1. Acts as a bridge between the UI and the repository layer.
 * 2. Provides real-time data streams (via Flows) to keep the UI updated with changes in the database.
 * 3. Exposes functions for CRUD operations (Create, Read, Update, Delete) on diary entries.
 *
 * Key Features:
 * - Provides a list of all diary entries via `diaryItems`, which is observed by the UI.
 * - Allows adding a new diary with the `addDiary` function.
 * - Supports deletion of diary entries using `removeDiary`.
 * - Fetches individual diaries by their ID using `getDiaryById`.
 * - Enables updating existing diary entries with `updateDiary`.
 *
 * This ViewModel leverages Kotlin Coroutines for asynchronous database operations,
 * ensuring that database interactions do not block the main thread.
 */
class DiariesViewModel(private val repository: DiaryRepository) : ViewModel() {

    // StateFlow representing the list of all diary entries.
    // This is actively observed by the UI for updates.
    val diaryItems = repository.getAllDiaries()

    // Function for adding a new diary
    fun addDiary(title: String, content: String, date: String) {
        viewModelScope.launch {
            repository.addDiary(DiaryEntity(title = title, content = content, date = date))
        }
    }

    // Function for removing a diary
    fun removeDiary(diary: DiaryEntity) {
        viewModelScope.launch {
            repository.removeDiary(diary)
        }
    }

    // Get a specific diary by its ID
    fun getDiaryById(id: String) = repository.getDiaryById(id)

    // Updates diary information
    fun updateDiary(diary: DiaryEntity) {
        viewModelScope.launch {
            repository.updateDiary(diary)
        }
    }
}
