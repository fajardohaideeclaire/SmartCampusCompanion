package com.example.smartcampuscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.example.smartcampuscompanion.ui.theme.SmartCampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Dark mode state lives here so it persists across screens
            var isDarkMode by remember { mutableStateOf(false) }

            SmartCampusTheme(darkTheme = isDarkMode) {
                val navController = rememberNavController()
                AppNav(
                    navController = navController,
                    isDarkMode = isDarkMode,
                    onDarkModeToggle = { isDarkMode = it }
                )
            }
        }
    }
}