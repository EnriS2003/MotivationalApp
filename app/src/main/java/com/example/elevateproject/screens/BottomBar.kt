package com.example.elevateproject.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

/**
 * Bottom navigation bar for the app.
 *
 * The `BottomBar` composable defines a consistent bottom navigation bar used throughout the app.
 * It provides quick access to major screens: Home, Diaries, Tasks, and Settings.
 *
 * Key Features:
 * - Navigation Integration: Each icon button navigates to a corresponding screen using the `NavController`.
 * - Intuitive Design: Icons represent the purpose of each screen for improved user experience.
 *
 * Parameters:
 * - `navController`: The `NavController` used to handle navigation between screens.
 *
 * Navigation Buttons:
 * - Home: Navigates to the "home" screen.
 * - Diaries: Navigates to the "diaries" screen.
 * - Tasks: Navigates to the "task" screen.
 * - Settings: Navigates to the "settings" screen.
 */
@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(Icons.Default.Home, contentDescription = "Home")
            }
            IconButton(onClick = { navController.navigate("diaries") }) {
                Icon(Icons.Default.DateRange, contentDescription = "Diaries")
            }
            IconButton(onClick = { navController.navigate("task") }) {
                Icon(Icons.Default.AddCircle, contentDescription = "Tasks")
            }
            IconButton(onClick = { navController.navigate("settings") }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings")
            }

        }
    }
}

