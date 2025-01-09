package com.example.elevateproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elevateproject.data.diaryData.DiaryEntity
import com.example.elevateproject.data.diaryData.DiaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DiariesViewModel(private val repository: DiaryRepository) : ViewModel() {

    // State for the list of diaries
    val diaryItems = repository.getAllDiaries()

    val allDiaries: Flow<List<DiaryEntity>> = repository.getAllDiaries()

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

    // Update a diary
    fun updateDiary(diary: DiaryEntity) {
        viewModelScope.launch {
            repository.updateDiary(diary)
        }
    }
}
