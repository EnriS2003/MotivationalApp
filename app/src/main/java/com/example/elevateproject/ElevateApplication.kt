package com.example.elevateproject

import android.app.Application
import com.example.elevateproject.data.DiaryDatabase
import com.example.elevateproject.data.diaryData.DiaryRepository
import com.example.elevateproject.data.quoteData.QuoteRepository
import com.example.elevateproject.data.tasksData.TaskRepository

/**
 * Custom Application class for initializing global resources such as the database and repositories.
 * This class provides shared access to these resources throughout the application lifecycle.
 */
class ElevateApplication : Application() {
    private lateinit var database: DiaryDatabase

    lateinit var diaryRepository: DiaryRepository
        private set

    lateinit var taskRepository: TaskRepository
        private set

    lateinit var quoteRepository: QuoteRepository
        private set

    override fun onCreate() {
        super.onCreate()
        database = DiaryDatabase.getDatabase(this)
        diaryRepository = DiaryRepository(database.diaryDao())
        taskRepository = TaskRepository(database.taskDao())
        quoteRepository = QuoteRepository(database.quoteDao())
    }
}

