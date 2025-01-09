package com.example.elevateproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.elevateproject.data.diaryData.DiaryDao
import com.example.elevateproject.data.diaryData.DiaryEntity
import com.example.elevateproject.data.tasksData.TaskDao
import com.example.elevateproject.data.tasksData.TaskEntity

/*
Represents the database of the application with the given entities and version.
 */
@Database(entities = [DiaryEntity::class, TaskEntity::class], version = 2, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: DiaryDatabase? = null

        /*
        Migration object for database version 1 to 2. This is needed to create the tasks_table
        without loosing existing data, like diary entries.
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
            CREATE TABLE IF NOT EXISTS tasks_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                deadline TEXT NOT NULL,
                isCompleted INTEGER NOT NULL DEFAULT 0
            )
            """)
            }
        }

        fun getDatabase(context: Context): DiaryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiaryDatabase::class.java,
                    "diary_database"
                )
                    .addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

