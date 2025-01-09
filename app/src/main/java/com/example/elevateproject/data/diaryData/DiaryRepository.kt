package com.example.elevateproject.data.diaryData

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


    suspend fun updateDiary(diary: DiaryEntity) {
        diaryDao.updateDiary(diary)
    }

}

