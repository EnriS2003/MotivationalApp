package com.example.elevateproject.data.tasksData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
/*
 * Represents a task in the database.
 */
@Entity(tableName = "tasks_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "deadline") val deadline: String,
    @ColumnInfo(name = "isCompleted") val isCompleted: Boolean = false
)


