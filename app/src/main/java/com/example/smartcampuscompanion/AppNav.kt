package com.example.smartcampuscompanion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

    // Check session on app start — skip login if already logged in
    val sessionManager = remember { SessionManager(context) }
    val startDestination = if (sessionManager.isLoggedIn()) "dashboard" else "login"

    val announcementRepository = remember {
        AnnouncementRepository(database.announcementDao())
    }
    val announcementViewModel: AnnouncementViewModel = viewModel(
        factory = AnnouncementViewModelFactory(announcementRepository)
    )

    NavHost(navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                announcementViewModel = announcementViewModel
            )
        }
        composable("campus") {
            CampusInfoScreen(navController)
        }
        composable("tasks") {
            val taskRepository = remember {
                TaskRepository(database.taskDao())
            }
            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(taskRepository)
            )
            TaskScreen(taskViewModel, navController)
        }
        composable("announcements") {
            AnnouncementScreen(announcementViewModel, navController)
        }
    }
}