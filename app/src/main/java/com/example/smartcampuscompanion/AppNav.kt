package com.example.smartcampuscompanion

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNav(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }
        composable("campus") { CampusInfoScreen(navController) }
        composable("tasks") {

            val context = LocalContext.current
            val database = AppDatabase.getDatabase(context)

            val repository = TaskRepository(database.taskDao())
            val viewModel = TaskViewModel(repository)

            TaskScreen(viewModel)
        }
        composable("announcements") {

            val context = LocalContext.current
            val database = AppDatabase.getDatabase(context)

            val repository = AnnouncementRepository(database.announcementDao())
            val viewModel = AnnouncementViewModel(repository)

            AnnouncementScreen(viewModel)
        }

    }
}
