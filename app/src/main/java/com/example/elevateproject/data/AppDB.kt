package com.example.elevateproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.elevateproject.data.diaryData.DiaryDao
import com.example.elevateproject.data.diaryData.DiaryEntity
import com.example.elevateproject.data.quoteData.QuoteDao
import com.example.elevateproject.data.quoteData.QuoteEntity
import com.example.elevateproject.data.tasksData.TaskDao
import com.example.elevateproject.data.tasksData.TaskEntity

/*
Represents the database of the application with the given entities and version.
 */
@Database(entities = [DiaryEntity::class, TaskEntity::class, QuoteEntity::class], version = 3, exportSchema = false)
abstract class DiaryDatabase : RoomDatabase() {

    abstract fun diaryDao(): DiaryDao
    abstract fun taskDao(): TaskDao
    abstract fun quoteDao(): QuoteDao

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

        // Migration object for database version 2 to 3.
        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("""
                    CREATE TABLE IF NOT EXISTS quotes_table (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        quote TEXT NOT NULL,
                        author TEXT NOT NULL,
                        date TEXT NOT NULL
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
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3) //Add the migration here
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

