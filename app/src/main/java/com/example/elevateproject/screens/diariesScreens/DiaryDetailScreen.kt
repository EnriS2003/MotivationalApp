package com.example.elevateproject.screens.diariesScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.elevateproject.screens.BottomBar
import com.example.elevateproject.viewmodels.DiariesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetailScreen(
    navController: NavController,
    diaryId: String,
    viewModel: DiariesViewModel
) {
    // Observe the diary from DB using the diaryId
    val diary by viewModel.getDiaryById(diaryId).collectAsState(initial = null)

    // Local variable to store the content of the diary
    var content by remember { mutableStateOf("") }

    // Updates the content of the diary when the diary is available
    diary?.let {
        content = it.content
    }

    Scaffold(
        bottomBar = { BottomBar(navController) },
        topBar = {
            TopAppBar(
                title = { Text("Diary - ${diary?.title ?: "Diary Details"}") },
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(horizontal = 16.dp) // Lateral padding
                .verticalScroll(rememberScrollState()), // Scrollable interface
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Input for the content of the diary
            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                maxLines = Int.MAX_VALUE,
                shape = MaterialTheme.shapes.medium
            )

            // Button for saving the diary
            Button(
                onClick = {
                    // Updates diary only if diary is not null
                    diary?.let { currentDiary ->
                        val updatedDiary = currentDiary.copy(content = content)
                        viewModel.updateDiary(updatedDiary)
                    }
                    navController.navigate("diaries")
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top=16.dp)
            ) {
                Text("Save")
            }
        }
    }
}
