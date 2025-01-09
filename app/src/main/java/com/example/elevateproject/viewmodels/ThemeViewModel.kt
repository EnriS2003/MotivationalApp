package com.example.elevateproject.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ThemeViewModel : ViewModel() {
    private val _theme = MutableStateFlow("System Default")
    val theme: StateFlow<String> = _theme

    fun setTheme(newTheme: String) {
        _theme.value = newTheme
    }
}

