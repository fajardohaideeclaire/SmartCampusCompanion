package com.example.smartcampuscompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import com.example.smartcampuscompanion.ui.theme.SmartCampusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartCampusTheme {
                val navController = rememberNavController()

                // Handle back button on dashboard
                BackHandler(enabled = navController.currentBackStackEntry?.destination?.route == "dashboard") {
                    finish() // Exit app
                }

                AppNav(navController = navController)
            }
        }
    }
}
