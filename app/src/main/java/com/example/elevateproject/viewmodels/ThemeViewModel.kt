package com.example.elevateproject.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * ViewModel for managing the app's theme settings.
 *
 * The `ThemeViewModel` is responsible for maintaining and updating the application's theme.
 * It uses a `MutableStateFlow` to hold the current theme and provides a `StateFlow` for observing
 * changes in the theme. This ensures a reactive approach to updating the UI when the theme is changed.
 *
 * Features:
 * - **Reactivity**: Leveraging Kotlin's `StateFlow`, the theme changes propagate to the UI in real-time,
 *   allowing seamless updates without manual intervention.
 * - **Encapsulation**: The `_theme` property is private, ensuring that only the `setTheme` method can
 *   modify its value, thereby maintaining control over the state.
 */

class ThemeViewModel : ViewModel() {
    private val _theme = MutableStateFlow("System Default")
    val theme: StateFlow<String> = _theme

    fun setTheme(newTheme: String) {
        _theme.value = newTheme
    }
}

