package com.example.elevateproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.elevateproject.viewmodels.ThemeViewModel

@Composable
fun AppTheme(viewModel: ThemeViewModel, content: @Composable () -> Unit) {

    // Collects the current theme from the ViewModel
    val selectedTheme by viewModel.theme.collectAsState()

    // Determines whether the app should use the dark theme
    val isDarkTheme = when (selectedTheme) {
        "Light" -> false
        "Dark" -> true
        else -> isSystemInDarkTheme() // Default to system setting
    }

    // Applies the theme to the content
    ElevateProjectTheme(darkTheme = isDarkTheme) {
        content()
    }
}
