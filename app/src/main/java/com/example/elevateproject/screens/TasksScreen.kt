package com.example.elevateproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.elevateproject.data.tasksData.Task
import com.example.elevateproject.viewmodels.TaskViewModel
import kotlinx.coroutines.launch

/**
 * TasksScreen
 *
 * This file defines the user interface and logic for managing tasks in the application.
 * It includes the following main components:
 *
 * - `TaskScreen`: The main screen displaying the list of tasks. It allows users to add new tasks,
 *   view existing ones, and filter completed tasks.
 * - `TaskItem`: A composable component that represents an individual task, along with its
 *   associated actions such as marking as completed or deleting.
 * - `AddTaskScreen`: A screen for adding new tasks, including fields for the task title and deadline.
 *
 * Key Features:
 * - Dynamic task list display.
 * - Filtering of completed tasks.
 * - Ability to add, update, or delete tasks.
 * - Utilizes Material Design 3 components for a modern and responsive UI.
 * - Automatic change between Navigation Bar and Navigation Rail based on screen size.
 *
 * Main Dependencies:
 * - ViewModel for managing task state.
 * - Repository for database interactions related to tasks.
 * - `NavController` for navigation between screens.
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var showExpiredTasks by remember { mutableStateOf(false) } // State of the filter

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Tasks") },
                actions = {
                    IconButton(onClick = { showExpiredTasks = !showExpiredTasks }) {
                        Icon(
                            Icons.Default.Menu,
                            contentDescription = "Filter Tasks",
                            tint = if (showExpiredTasks) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addTask") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        bottomBar = {
            if (!isLandscape()) {
                BottomBar(navController)
            }
        },
    ) { padding ->
        Row {
            if (isLandscape()) {
                NavigationRail(navController)
            }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
            ) {
                if (tasks.isEmpty()) {
                    // Message if no tasks
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tasks available. Add a new task!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                } else {
                    // Task list
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        val tasksToShow =
                            if (showExpiredTasks) viewModel.getExpiredTasks() else tasks
                        items(tasksToShow) { task ->
                            TaskItem(
                                task = task,
                                onMarkCompleted = {
                                    if (task.isCompleted) {
                                        viewModel.unmarkTaskAsCompleted(task)
                                    } else {
                                        viewModel.markTaskAsCompleted(task)
                                    }
                                },
                                onDelete = { viewModel.deleteTask(task) }
                            )
                        }
                    }
                }
            }
        }

    }
}

/**
 * TaskItem
 *
 * Displays a single task in the task list.
 *
 * Highlights:
 * - Adjusts the card's background color based on the task's completion status or if the deadline has passed.
 * - Provides actions for marking the task as completed or deleting it.
 * - Uses `remember` to compute if the deadline has passed for the task dynamically.
 * - Automatic change between Navigation Bar and Navigation Rail based on screen size.
 *
 * Parameters:
 * - `task`: The task to display.
 * - `onMarkCompleted`: Callback to mark the task as completed or undo completion.
 * - `onDelete`: Callback to delete the task.
 */
@Composable
fun TaskItem(task: Task, onMarkCompleted: () -> Unit, onDelete: () -> Unit) {
    // Verify if the task has passed its deadline
    val isDatePassed = remember(task.deadline) {
        try {
            val formatter = java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
            val deadlineDate = formatter.parse(task.deadline)
            deadlineDate?.before(java.util.Date()) == true
        } catch (e: Exception) {
            false // Handling exceptions
        }
    }

    // Set the card color based on the task status
    val cardColor = when {
        task.isCompleted -> MaterialTheme.colorScheme.primaryContainer
        isDatePassed -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    task.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
                Text(task.deadline, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onMarkCompleted) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = if (task.isCompleted) "Unmark as Done" else "Mark as Done",
                    tint = if (task.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Task")
            }
        }
    }
}

/**
 * AddTaskScreen
 *
 * Screen for adding a new task to the task list.
 *
 * Highlights:
 * - Includes input fields for the task title and deadline.
 * - Provides a date picker dialog for selecting the deadline.
 * - Validates input fields and shows a snackbar for missing fields.
 * - Uses `SnackbarHost` to display messages for invalid input.
 * - Automatic change between Navigation Bar and Navigation Rail based on screen size.
 *
 * Parameters:
 * - `navController`: Handles navigation between screens.
 * - `viewModel`: Manages the task state and interactions.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            if (!isLandscape()) {
                BottomBar(navController)
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Add Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Row {
            if (isLandscape()) {
                NavigationRail(navController)
            }
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Use a custom date picker
                ManualDateInput(
                    selectedDate = deadline,
                    onDateSelected = { newDate ->
                        deadline = newDate
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))
                // Save Button with delayed snackbar for empty fields
                Button(
                    onClick = {
                        when {
                            title.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Task title cannot be empty!")
                                }
                            }

                            deadline.isBlank() -> {
                                coroutineScope.launch {
                                    snackbarHostState.showSnackbar("Deadline date format is wrong or is empty!")
                                }
                            }

                            else -> {
                                viewModel.addTask(title, deadline)
                                navController.popBackStack() // Go back to the previous screen
                            }
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Add Task")
                }
            }
        }

    }
}

/**
 * ManualDateInput
 *
 * A composable function that provides a manual date input field for users to enter a date in the format "dd/MM/yyyy".
 *
 * Key Features:
 * - Validates user input to ensure it follows the "dd/MM/yyyy" format.
 * - Ensures the month is valid (1-12) by leveraging a regex pattern.
 * - Calls the `onDateSelected` callback with the valid date string whenever a valid input is detected.
 * - Uses an `OutlinedTextField` for input, making it visually consistent with Material Design guidelines.
 *
 * Parameters:
 * - `selectedDate`: The initial date value to display in the input field.
 * - `onDateSelected`: A callback function triggered when a valid date is entered.
 *
 * Notes:
 * - The regex pattern validates the day (`\\d{2}`), the month (`0[1-9]`, `1[0-2]`, or `[1-9]` for single-digit months),
 *   and the year (`\\d{4}`).
 * - Input validation ensures users enter valid months (e.g., no 13 or 00).
 * - The component is reusable and can be embedded within other composables for date input functionality.
 */
@Composable
fun ManualDateInput(selectedDate: String, onDateSelected: (String) -> Unit) {
    var inputDate by remember { mutableStateOf(selectedDate) }
    OutlinedTextField(
        value = inputDate,
        onValueChange = {
            inputDate = it
            // Validation
            val regex = Regex("^\\d{2}/(0[1-9]|1[0-2]|[1-9])/\\d{4}$")
            if (regex.matches(it)) {
                onDateSelected(it)
            }
        },
        label = { Text("Enter Date (dd/MM/yyyy)") },
        modifier = Modifier.fillMaxWidth()
    )
}




