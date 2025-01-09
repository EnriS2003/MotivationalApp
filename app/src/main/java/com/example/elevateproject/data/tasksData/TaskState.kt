package com.example.elevateproject.data.tasksData

data class Task(
    val id: Int = 0,
    val title: String,
    val deadline: String,
    val isCompleted: Boolean
)