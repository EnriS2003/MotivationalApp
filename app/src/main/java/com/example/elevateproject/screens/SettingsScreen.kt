package com.example.elevateproject.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.elevateproject.ui.theme.ElevateProjectTheme
import com.example.elevateproject.viewmodels.ThemeViewModel

/**
 * Composable function for displaying the Settings screen.
 *
 * This screen provides various settings options for the app, such as theme selection and app information.
 * It also includes navigation functionality via the bottom bar.
 *
 * @param navController The navigation controller for navigating between screens.
 * @param viewModel The `ThemeViewModel` that provides the state and logic for theme selection.
 */
@Composable
fun SettingsScreen(navController: NavController, viewModel: ThemeViewModel) {
    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Settings", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(bottom = 24.dp))

            // Theme of the app
            Text("Theme", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp).padding(bottom=26.dp).padding(top=24.dp))
            ThemeSetting(viewModel)
            Spacer(modifier = Modifier.height(24.dp))

            // About the app
            Text("About the App", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top=24.dp).padding(bottom = 8.dp))
            Text(
                text = "Developed by: Enri Sulejmani",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Version: 1.0.0",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "© 2025 UniBz project. All rights reserved.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 16.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Composable function for displaying the theme selection options.
 *
 * Allows users to switch between Light, Dark, and System Default themes.
 *
 * @param themeViewModel The `ThemeViewModel` managing the state and logic for theme selection.
 */
@Composable
fun ThemeSetting(themeViewModel: ThemeViewModel) {
    val selectedTheme by themeViewModel.theme.collectAsState()
    val themes = listOf("Light", "Dark", "System Default")

    Column {
        themes.forEach { theme ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            ) {
                RadioButton(
                    selected = (theme == selectedTheme), // Use the selected theme
                    onClick = { themeViewModel.setTheme(theme) } // Update the theme
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(theme) // Display the theme name.
            }
        }
    }
}

/**
 * Preview function for the Settings screen.
 *
 * This simulates the `SettingsScreen` for testing and visualization in the IDE preview.
 */
@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    // Simulate the ViewModel with a default theme set to "System Default".
    val themeViewModel = remember {
        ThemeViewModel().apply {
            setTheme("System Default") // Set the initial theme
        }
    }
    // Apply the theme and display the Settings screen preview.
    ElevateProjectTheme {
        SettingsScreen(navController = NavController(LocalContext.current), viewModel = themeViewModel)
    }
}
