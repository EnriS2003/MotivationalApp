package com.example.elevateproject

import android.app.Application
import com.example.elevateproject.data.DiaryDatabase
import com.example.elevateproject.data.diaryData.DiaryRepository
import com.example.elevateproject.data.quoteData.QuoteRepository
import com.example.elevateproject.data.tasksData.TaskRepository

/**
 * Custom Application class for initializing global resources such as the database and repositories.
 *
 * This class is responsible for setting up and providing access to shared application-level resources.
 * It is initialized once during the application's lifecycle and ensures that the required resources
 * are available throughout the app.
 *
 * Key Responsibilities:
 * - **Database Initialization**: The `DiaryDatabase` is created and initialized here. This ensures that
 *   a single instance of the database is used across the app, adhering to the Singleton pattern.
 * - **Repository Initialization**: Repositories for accessing diaries, tasks, and quotes are created
 *   using the DAO (Data Access Object) from the initialized database. These repositories act as
 *   intermediaries between the data layer and the ViewModel layer, promoting clean architecture.
 *
 * Benefits:
 * - Centralized initialization of critical resources ensures consistency and avoids multiple instances.
 * - Simplifies dependency management by making repositories globally accessible where needed.
 *
 * Lifecycle:
 * - The `onCreate` method is called when the application is first created. Here, the database and
 *   repositories are initialized.
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

