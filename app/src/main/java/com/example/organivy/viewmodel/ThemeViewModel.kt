package com.example.organivy.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.organivy.ui.pages.ThemeOption

class ThemeViewModel : ViewModel() {
    var selectedTheme by mutableStateOf(ThemeOption.SYSTEM)
        private set

    fun setTheme(theme: ThemeOption) {
        selectedTheme = theme
    }
}
