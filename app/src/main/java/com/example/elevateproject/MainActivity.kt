package com.example.elevateproject

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.elevateproject.ui.theme.AppTheme
import com.example.elevateproject.viewmodels.DiariesViewModel
import com.example.elevateproject.viewmodels.QuoteViewModel
import com.example.elevateproject.viewmodels.TaskViewModel
import com.example.elevateproject.viewmodels.ThemeViewModel

/**
 * Main entry point of the application.
 *
 * This activity sets up the Compose environment, applies the app's theme, and initializes the navigation graph.
 * It also provides instances of the ViewModels needed across the app.
 */

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Obtain instance from ElevateApplication
        val application = application as ElevateApplication
        val diaryRepository = application.diaryRepository
        val taskRepository = application.taskRepository

        // Initialize the view models
        val diariesViewModel = DiariesViewModel(diaryRepository)
        val taskViewModel = TaskViewModel(taskRepository)
        val quoteViewModel = QuoteViewModel(application.quoteRepository)

        setContent {
            // Initialize the theme view model
            val themeViewModel: ThemeViewModel = viewModel()

            AppTheme(viewModel = themeViewModel) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    val navController = rememberNavController()
                    AppNavHost(
                        navController = navController,
                        diariesViewModel = diariesViewModel,
                        taskViewModel = taskViewModel,
                        quoteViewModel = quoteViewModel
                    )
                }
            }
        }
    }
}