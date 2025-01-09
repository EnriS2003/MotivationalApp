package com.example.elevateproject

import android.app.Application
import com.example.elevateproject.data.DiaryDatabase
import com.example.elevateproject.data.diaryData.DiaryRepository
import com.example.elevateproject.data.tasksData.TaskRepository

class ElevateApplication : Application() {
    private lateinit var database: DiaryDatabase

    lateinit var diaryRepository: DiaryRepository
        private set

    lateinit var taskRepository: TaskRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = DiaryDatabase.getDatabase(this)
        diaryRepository = DiaryRepository(database.diaryDao())
        taskRepository = TaskRepository(database.taskDao())
    }
}

