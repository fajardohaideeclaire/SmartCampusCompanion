package com.example.smartcampuscompanion

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.ui.platform.LocalContext
import com.example.smartcampuscompanion.data.local.AppDatabase
import com.example.smartcampuscompanion.data.repository.AnnouncementRepository
import com.example.smartcampuscompanion.data.repository.TaskRepository
import com.example.smartcampuscompanion.firebase.FirebaseService
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModelFactory
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import com.example.smartcampuscompanion.viewmodel.TaskViewModelFactory
import com.example.smartcampuscompanion.ui.announcement.AnnouncementScreen
import com.example.smartcampuscompanion.ui.task.TaskScreen

@Composable
fun AppNav(
    navController: NavHostController,
    isDarkMode: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)
    val sessionManager = remember { SessionManager(context) }

    val startDestination = when {
        !sessionManager.isLoggedIn()               -> "login"
        sessionManager.getRole() == UserRole.ADMIN -> "admin"
        else                                        -> "dashboard"
    }

    val announcementRepository = remember {
        AnnouncementRepository(
            dao = database.announcementDao(),
            firebaseService = FirebaseService()
        )
    }
    val announcementViewModel: AnnouncementViewModel = viewModel(
        factory = AnnouncementViewModelFactory(announcementRepository)
    )

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("dashboard") {
            DashboardScreen(
                navController = navController,
                announcementViewModel = announcementViewModel
            )
        }
        composable("admin") {
            AdminScreen(
                navController = navController,
                announcementViewModel = announcementViewModel
            )
        }
        composable("campus") {
            CampusInfoScreen(navController)
        }
        composable("tasks") {
            val taskRepository = remember { TaskRepository(database.taskDao()) }
            val taskViewModel: TaskViewModel = viewModel(
                factory = TaskViewModelFactory(taskRepository)
            )
            TaskScreen(taskViewModel, navController)
        }
        composable("announcements") {
            AnnouncementScreen(announcementViewModel, navController)
        }
        composable("settings") {
            SettingsScreen(
                navController = navController,
                isDarkMode = isDarkMode,
                onDarkModeToggle = onDarkModeToggle
            )
        }
        composable("profile") {
            ProfileScreen(navController)
        }
    }
}