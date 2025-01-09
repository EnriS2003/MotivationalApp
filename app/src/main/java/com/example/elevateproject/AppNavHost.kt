package com.example.elevateproject

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.elevateproject.screens.AddDiaryScreen
import com.example.elevateproject.screens.DiariesScreen
import com.example.elevateproject.screens.DiaryDetailScreen
import com.example.elevateproject.screens.HomeScreen
import com.example.elevateproject.screens.SettingsScreen
import com.example.elevateproject.screens.AddTaskScreen
import com.example.elevateproject.screens.FavoritesScreen
import com.example.elevateproject.screens.TasksScreen
import com.example.elevateproject.viewmodels.DiariesViewModel
import com.example.elevateproject.viewmodels.QuoteViewModel
import com.example.elevateproject.viewmodels.TaskViewModel
import com.example.elevateproject.viewmodels.ThemeViewModel

/**
 * Navigation host for the app.
 *
 * This composable sets up the navigation graph for the application,
 * defining the routes and their corresponding screens. Each screen is associated with a
 * ViewModel for managing its state and logic. The navigation graph supports various features:
 * - Home screen: Displays quotes and allows navigation to other sections.
 * - Diary-related screens: Includes viewing, adding, and editing diary entries.
 * - Task-related screens: Includes viewing tasks, adding new tasks, and filtering tasks.
 * - Settings screen: Allows the user to customize the app theme.
 *
 * The `NavHost` component handles the transitions between screens, and the ViewModels are
 * injected using the `viewModel()` function, ensuring they persist across configuration changes.
 *
 * @param navController The NavHostController that manages the navigation stack.
 * @param startDestination The starting screen for the app, defaulting to "home".
 * @param diariesViewModel The ViewModel for managing diary-related operations.
 * @param taskViewModel The ViewModel for managing task-related operations.
 * @param themeViewModel The ViewModel for handling theme customization.
 * @param quoteViewModel The ViewModel for fetching and saving quotes.
 */

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(
    // If no such viewModel's instance exists, it will be created. Otherwise, the created instance will be used.
    navController: NavHostController,
    startDestination: String = "home",
    diariesViewModel: DiariesViewModel = viewModel(),
    taskViewModel: TaskViewModel = viewModel(),
    themeViewModel: ThemeViewModel = viewModel(),
    quoteViewModel: QuoteViewModel = viewModel()
) {

    NavHost(navController = navController, startDestination = startDestination) {

        // Navigation for the home screen
        composable("home") { HomeScreen(navController = navController, viewModel = quoteViewModel) }

        // Navigation for the diary detail screen
        composable(
            route = "diaryDetail/{diaryId}",
            arguments = listOf(navArgument("diaryId") {
                type = androidx.navigation.NavType.StringType
            })
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
            TasksScreen(navController = navController, viewModel = taskViewModel)
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
        // Navigation for the favorites screen
        composable("favorites") {
            FavoritesScreen(navController = navController, viewModel = quoteViewModel)
        }

    }
}