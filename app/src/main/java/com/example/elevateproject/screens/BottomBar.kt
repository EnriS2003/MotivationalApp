package com.example.elevateproject.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.ui.unit.dp

/**
 * Responsive Navigation for the app.
 *
 * This file defines a responsive navigation system for the app that adapts
 * based on the device's orientation:
 * - Displays a `BottomBar` in portrait mode.
 * - Displays a `NavigationRail` in landscape mode.
 *
 * Key Features:
 * - Dynamic layout switching between bottom navigation and navigation rail.
 * - Navigation Integration: Each navigation item uses the `NavController` to
 *   navigate between screens.
 * - Design: Icons and labels represent the purpose of each screen.
 *
 * Components:
 * - `BottomBar`: A traditional bottom navigation bar with quick access to major screens.
 * - `NavigationRail`: A sidebar-like navigation rail for landscape mode.
 * - `ResponsiveNavigation`: Determines the navigation component to display based
 *   on the device orientation.
 * - `isLandscape`: A helper function to check the device orientation.
 *
 * Navigation Buttons:
 * - Home: Navigates to the "home" screen.
 * - Diaries: Navigates to the "diaries" screen.
 * - Tasks: Navigates to the "task" screen.
 * - Settings: Navigates to the "settings" screen.
 *
 * Parameters:
 * - `navController`: The `NavController` used to handle navigation between screens.
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

@Composable
fun NavigationRail(
    navController: NavController,
) {
    NavigationRail (
        modifier = Modifier.padding(top=56.dp, end = 8.dp)
    )
    {
        NavigationRailItem(
            selected = false,
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") }
        )
        NavigationRailItem(
            selected = false,
            onClick = { navController.navigate("diaries") },
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Diaries") },
            label = { Text("Diaries") }
        )
        NavigationRailItem(
            selected = false,
            onClick = { navController.navigate("task") },
            icon = { Icon(Icons.Default.AddCircle, contentDescription = "Tasks") },
            label = { Text("Tasks") }
        )
        NavigationRailItem(
            selected = false,
            onClick = { navController.navigate("settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") }
        )
    }
}

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
}