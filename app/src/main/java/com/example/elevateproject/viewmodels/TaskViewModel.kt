package com.example.elevateproject.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.elevateproject.data.tasksData.Task
import com.example.elevateproject.data.tasksData.TaskEntity
import com.example.elevateproject.data.tasksData.TaskRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * Repository for managing tasks.
 *
 * The `TaskRepository` serves as an abstraction layer between the `TaskDao` (data access object)
 * and the rest of the application. It encapsulates the logic for accessing and managing task-related data,
 * ensuring that the ViewModels interact with a clean and consistent interface.
 *
 * Properties:
 * - `allTasks`: A `Flow` that provides a real-time stream of all tasks stored in the database.
 *
 * Methods:
 * - `insertTask(task: TaskEntity)`: Inserts a new task into the database.
 * - `updateTask(task: TaskEntity)`: Updates an existing task in the database.
 * - `deleteTask(task: TaskEntity)`: Deletes a specific task from the database.
 * - `resetDatabase()`: Clears all tasks and diaries from the database by invoking DAO methods.
 *
 * Dependencies:
 * - `TaskDao`: Used to interact with the tasks-related operations in the database.
 *
 * Key Features:
 * - Separation of Concerns: This repository ensures that the ViewModels do not directly interact with
 *   the database, maintaining a clean architecture.
 * - Real-Time Updates: By leveraging Kotlin Flows, the repository ensures that changes to the task data
 *   are observed in real-time by the ViewModel and subsequently by the UI.
 * - Database Reset: Provides functionality to clear all tasks and diaries, useful for resetting the application state.
 */
class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // StateFlow for holding the list of tasks
    val tasks: StateFlow<List<Task>> = repository.allTasks
        .map { entities -> entities.map { it.toTask() } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    @RequiresApi(Build.VERSION_CODES.O)
    fun getExpiredTasks(): List<Task> {
        val currentDate = LocalDate.now()
        return tasks.value.filter { task ->
            try {
                val taskDate = LocalDate.parse(task.deadline, dateFormatter)
                taskDate.isBefore(currentDate)
            } catch (e: DateTimeParseException) {
                false // If parsing fails, consider it as not expired
            }
        }
    }

    fun addTask(title: String, deadline: String) {
        viewModelScope.launch {
            repository.insertTask(TaskEntity(title = title, deadline = deadline))
        }
    }

    fun markTaskAsCompleted(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.toEntity().copy(isCompleted = true)
            println("Updating task in database: $updatedTask")
            repository.updateTask(task.toEntity().copy(isCompleted = true))
        }
    }
    fun unmarkTaskAsCompleted(task: Task) {
        viewModelScope.launch {
            val updatedTask = task.toEntity().copy(isCompleted = false)
            println("Updating task in database: $updatedTask")
            repository.updateTask(task.toEntity().copy(isCompleted = false))
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task.toEntity())
        }
    }
}

// Extension functions to convert between Task and TaskEntity
fun TaskEntity.toTask() = Task(title = title, deadline = deadline, isCompleted = isCompleted, id = id)
fun Task.toEntity() = TaskEntity(title = title, deadline = deadline, isCompleted = isCompleted, id = id)
