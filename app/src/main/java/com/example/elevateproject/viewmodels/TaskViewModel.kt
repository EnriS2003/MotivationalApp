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
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException

/**
 * TaskViewModel
 *
 * ViewModel for managing tasks in the application.
 *
 * The `TaskViewModel` acts as a mediator between the UI layer and the `TaskRepository`.
 * It handles business logic for task-related features, ensuring that the UI can interact
 * with task data in a clean and structured manner.
 *
 * Key Features:
 * - **Real-Time Task Management**: The ViewModel uses `StateFlow` to provide real-time updates to the UI
 *   for any changes in the list of tasks stored in the database.
 * - **Task Expiration Logic**: Includes methods to check if tasks have expired based on their deadlines.
 * - **CRUD Operations**: Provides functionality to add, update, and delete tasks, ensuring
 *   encapsulation of the business logic.
 * - **Separation of Concerns**: Ensures that the UI layer does not interact directly with the database,
 *   adhering to the principles of clean architecture.
 *
 * Properties:
 * - `tasks`: A `StateFlow` holding the list of tasks, automatically updated when the database changes.
 *
 * Methods:
 * - `getExpiredTasks()`: Filters and returns tasks with deadlines earlier than the current date.
 * - `addTask(title: String, deadline: String)`: Adds a new task to the database.
 * - `markTaskAsCompleted(task: Task)`: Marks a task as completed by updating its status in the database.
 * - `unmarkTaskAsCompleted(task: Task)`: Reverts a task's status to not completed.
 * - `deleteTask(task: Task)`: Deletes a task from the database.
 *
 * Conversion Functions:
 * - `TaskEntity.toTask()`: Converts a `TaskEntity` (database model) into a `Task` (domain model).
 * - `Task.toEntity()`: Converts a `Task` (domain model) into a `TaskEntity` (database model).
 */

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatterBuilder()
        .appendPattern("dd/")
        .appendValue(java.time.temporal.ChronoField.MONTH_OF_YEAR)
        .appendPattern("/yyyy")
        .toFormatter()

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
