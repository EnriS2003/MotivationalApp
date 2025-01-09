package com.example.elevateproject.screens.diariesScreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.unit.sp
import com.example.elevateproject.screens.BottomBar
import com.example.elevateproject.viewmodels.DiariesViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiariesScreen(
    navController: NavController,
    viewModel: DiariesViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val diaryItems by viewModel.diaryItems.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = { BottomBar(navController) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate("addDiary")
            }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Your Diaries") },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (diaryItems.isEmpty()) {
                // Message when no diaries are available
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No diaries yet. Start by adding one!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(16.dp)
            ) {
                itemsIndexed(diaryItems) { _, diary ->

                    var isEditing by remember { mutableStateOf(false) }
                    var newTitle by remember { mutableStateOf(diary.title) }

                    Card(
                        onClick = {
                            val diaryId = diary.id
                            navController.navigate("diaryDetail/${diaryId}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(15.dp)
                        ) {
                            Text(
                                text = diary.title,
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 22.sp,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(
                                onClick = { isEditing = true } // Open edit mode
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = "Edit Diary Title")
                            }
                            IconButton(
                                onClick = { viewModel.removeDiary(diary) }
                            ) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete Diary")
                            }
                        }
                    }
                    if (isEditing) {
                        AlertDialog(
                            onDismissRequest = { isEditing = false },
                            title = { Text("Edit Title") },
                            text = {
                                TextField(
                                    value = newTitle,
                                    onValueChange = { newTitle = it },
                                    label = { Text("New Title") },
                                    singleLine = true
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        val updatedDiary = diary.copy(title = newTitle)
                                        viewModel.updateDiary(updatedDiary) // Save to DB
                                        isEditing = false
                                    }
                                ) {
                                    Text("Save")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { isEditing = false }) {
                                    Text("Cancel")
                                }
                            }
                        )
                    }
                }
            }
            } //if else statement closing bracket
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDiaryScreen(navController: NavController, viewModel: DiariesViewModel) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Diary") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.Menu, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } // Host for the Snackbar
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
                label = { Text("Diary Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Diary Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp), // Adjust height for multiline input
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    when {
                        title.isBlank() -> {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Please provide a title for the diary!")
                            }
                        }
                        else -> {
                            val date = LocalDateTime.now().toString()
                            viewModel.addDiary(title,content,date) // Save diary in DB
                            navController.popBackStack() // Go back to the previous screen
                        }
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Save Diary")
            }
        }
    }
}
