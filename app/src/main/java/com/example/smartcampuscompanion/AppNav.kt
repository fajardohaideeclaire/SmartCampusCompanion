package com.example.smartcampuscompanion

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import com.example.smartcampuscompanion.data.local.AppDatabase
import com.example.smartcampuscompanion.data.repository.TaskRepository
import com.example.smartcampuscompanion.data.repository.AnnouncementRepository
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import com.example.smartcampuscompanion.viewmodel.TaskViewModelFactory
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModelFactory
import com.example.smartcampuscompanion.ui.task.TaskScreen
import com.example.smartcampuscompanion.ui.announcement.AnnouncementScreen

@Composable
fun AppNav(navController: NavHostController) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(navController)
        }
        composable("campus") {
            CampusInfoScreen(navController)
        }
        composable("tasks") {
            val repository = TaskRepository(database.taskDao())
            val viewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(repository)
            )
            TaskScreen(viewModel, navController)
        }
        composable("announcements") {
            val repository = AnnouncementRepository(database.announcementDao())
            val viewModel: AnnouncementViewModel = viewModel(
                factory = AnnouncementViewModelFactory(repository)
            )
            AnnouncementScreen(viewModel, navController)
        }
    }
}