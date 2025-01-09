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

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

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
