package com.example.elevateproject.data.tasksData

import kotlinx.coroutines.flow.Flow

/**
 * TaskRepository
 *
 * Repository for managing tasks in the application.
 *
 * The `TaskRepository` acts as an abstraction layer between the `TaskDao` (data access object)
 * and the rest of the application, providing a clean interface for interacting with task-related data.
 *
 * Key Features:
 * - **Data Flow Management**: Exposes a `Flow` of all tasks (`allTasks`) to provide real-time updates
 *   to consumers, ensuring changes in the database are reflected immediately in the UI.
 * - **Encapsulation**: Encapsulates database operations, allowing the rest of the application
 *   to interact with task data without knowing the details of the database implementation.
 * - **CRUD Operations**: Provides methods for creating, reading, updating, and deleting tasks in the database.
 *
 * Properties:
 * - `allTasks`: A `Flow` that emits the list of tasks from the database in real-time.
 *
 * Methods:
 * - `insertTask(task: TaskEntity)`: Adds a new task to the database.
 * - `updateTask(task: TaskEntity)`: Updates the details of an existing task in the database.
 * - `deleteTask(task: TaskEntity)`: Removes a task from the database.
 */

class TaskRepository(private val taskDao: TaskDao) {
    val allTasks: Flow<List<TaskEntity>> = taskDao.getAllTasks()

    suspend fun insertTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}
