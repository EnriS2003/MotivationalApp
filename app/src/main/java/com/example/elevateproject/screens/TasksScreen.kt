package com.example.elevateproject.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.elevateproject.data.tasksData.Task
import com.example.elevateproject.viewmodels.TaskViewModel
import kotlinx.coroutines.launch

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
        bottomBar = { BottomBar(navController) }
    ) { padding ->
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
                    val tasksToShow = if (showExpiredTasks) viewModel.getExpiredTasks() else tasks
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

/*
TaskItem composable represents a single task in the list.
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController, viewModel: TaskViewModel) {
    var title by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) } // State to control the date picker dialog
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
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
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
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
            // Deadline Picker
            OutlinedTextField(
                value = deadline,
                onValueChange = {},
                label = { Text("Deadline (press + icon)  -->") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { showDatePicker = true }) {
                        Icon(Icons.Default.Add, contentDescription = "Pick Date")
                    }
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
                                snackbarHostState.showSnackbar("Deadline cannot be empty!")
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
            // DatePicker Dialog
            if (showDatePicker) {
                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },
                    onDateSelected = { selectedDate ->
                        deadline = selectedDate
                        showDatePicker = false
                    }
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    onDismissRequest: () -> Unit,
    onDateSelected: (String) -> Unit
) {
    val datePickerState = rememberDatePickerState()

    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            TextButton(onClick = {
                val selectedDateMillis = datePickerState.selectedDateMillis
                val selectedDate = selectedDateMillis?.let {
                    java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault())
                        .format(java.util.Date(it))
                } ?: ""
                onDateSelected(selectedDate)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissRequest) {
                Text("Cancel")
            }
        },
        text = {
            // Wrap the DatePicker in a Box for scaling and padding for layout purposes
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .scale(0.9f)
                    .padding(16.dp)
            ) {
                DatePicker(state = datePickerState)
            }
        }
    )
}


