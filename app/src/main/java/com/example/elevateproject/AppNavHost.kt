package com.example.elevateproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.elevateproject.screens.diariesScreens.AddDiaryScreen
import com.example.elevateproject.screens.diariesScreens.DiariesScreen
import com.example.elevateproject.screens.diariesScreens.DiaryDetailScreen
import com.example.elevateproject.screens.HomeScreen
import com.example.elevateproject.screens.SettingsScreen
import com.example.elevateproject.screens.taskScreens.AddTaskScreen
import com.example.elevateproject.screens.taskScreens.TaskScreen
import com.example.elevateproject.viewmodels.DiariesViewModel
import com.example.elevateproject.viewmodels.TaskViewModel
import com.example.elevateproject.viewmodels.ThemeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    navController: NavHostController,
    startDestination: String = "home",
    diariesViewModel: DiariesViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    themeViewModel: ThemeViewModel = viewModel()
) {

    NavHost(navController = navController, startDestination = startDestination) {

        // Navigation for the home screen
        composable("home") { HomeScreen(navController = navController) }

        // Navigation for the diary detail screen
        composable(
            route = "diaryDetail/{diaryId}",
            arguments = listOf(navArgument("diaryId") { type = androidx.navigation.NavType.StringType })
        ) { backStackEntry ->
            val diaryId = backStackEntry.arguments?.getString("diaryId") ?: "Unknown"
                DiaryDetailScreen(
                    navController = navController,
                    diaryId = diaryId,
                    viewModel = diariesViewModel
                )
        }

        // Navigation for the tasks screen
        composable("task") {
            TaskScreen(navController = navController, viewModel = taskViewModel)
        }

        // Navigation for the settings screen
        composable("settings") {
            SettingsScreen(navController = navController, viewModel = themeViewModel)
        }

        // Navigation for the diaries screen
        composable("diaries") {
            DiariesScreen(navController = navController, viewModel = diariesViewModel)
        }
        // Navigation for adding a new diary
        composable("addDiary") {
            AddDiaryScreen(navController = navController, viewModel = diariesViewModel)
        }
        // Navigation for adding a new task
        composable("addTask") {
            AddTaskScreen(navController = navController, viewModel = taskViewModel)
        }
    }
}