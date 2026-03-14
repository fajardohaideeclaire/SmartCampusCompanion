# Midterm Reflection

Smart Campus Companion — Phase 2
Course: Mobile Application Development
Tag: v1.0-midterm

---

## Team Members & Roles

- Fajardo — Team Leader / Lead Developer (MVVM architecture, Room database setup, system integration)
- Dacillo — Feature Developer (Task Manager feature implementation)
- Fernandez — UI/UX Developer (screen design and layout improvements)
- De Leon — Git Manager (repository setup, branch management, pull request coordination)
- Del Mundo — QA & Documenter (testing, documentation, changelog, and reports)

---

## Git Workflow

The team used a feature branch workflow in GitHub.

Branches used in the project:

- master — stable production-ready code
- develop — integration branch for completed features
- teamleader-fajardo — architecture and system updates
- feature-dacillo — Task Manager feature
- ui/ux-fernandez — UI and layout improvements
- git-deLeon — repository and Git configuration tasks
- qa-delmundo — documentation and testing

Each member worked on their assigned branch and submitted changes through Pull Requests, which were reviewed and merged into the develop branch before final integration.

---

## Documented Merge Conflict

### What Happened

A merge conflict occurred when integrating updates into the project. Both branches modified AppNav.kt, specifically the NavHost composable.

One branch added the tasks route with ViewModel initialization, while another branch modified the dashboard route. Because both branches changed the same part of the file, Git could not automatically merge the changes.

### Conflict Example

<<<<<<< feature-task-manager
composable("tasks") {
val repository = TaskRepository(database.taskDao())
val viewModel: TaskViewModel = viewModel(factory = TaskViewModelFactory(repository))
TaskScreen(viewModel, navController)
}
=======
composable("dashboard") {
DashboardScreen(navController)
}
>>>>>>> main

### Resolution

The team manually edited AppNav.kt to include both routes while keeping the correct ViewModel factory implementation. After testing the application to ensure both screens worked correctly, the resolved file was committed with the message:

fix: resolve merge conflict in AppNav.kt

---

## Challenges Encountered

### 1. ViewModel State Loss

ViewModels were initially created manually inside composable functions. This caused them to be recreated during recomposition, resulting in lost task data.

Solution:
A ViewModelFactory was implemented and used when creating ViewModels.

---

### 2. Empty Announcements List

The AnnouncementViewModel contained a function for inserting sample announcements, but it was not triggered during startup.

Solution:
The seeding logic was moved into the init block with a condition that inserts data only when the database is empty.

---

### 3. Login Without Validation

Originally, the login button navigated directly to the dashboard regardless of the credentials entered.

Solution:
The team integrated AuthUtils for credential validation and SessionManager for storing login sessions.

---

### 4. Git Workflow Issues

At the beginning of the project, some commits were pushed directly to the main branch.

Solution:
The team adopted a stricter workflow where all development occurs in feature branches and changes are merged through Pull Requests.

---

## Architecture Overview

The application follows the MVVM (Model–View–ViewModel) architecture.

Layers used in the system:

UI Layer
- Jetpack Compose screens (Login, Dashboard, Tasks, Announcements, Campus Info)

ViewModel Layer
- Manages UI state using StateFlow

Repository Layer
- Handles communication between ViewModels and the database

Data Layer
- Room Database with DAOs and Entities

Data Flow:

Compose UI
↓
ViewModel
↓
Repository
↓
Room Database

---

## Reflection Summary

Phase 2 demonstrated the importance of proper architecture and teamwork in mobile application development. Implementing MVVM and Room Database improved data management and allowed the app to update dynamically through StateFlow. The team also improved collaboration practices after encountering a merge conflict, which reinforced the importance of using feature branches and pull requests. Overall, the project provided valuable experience in Android development, version control, and collaborative software development.
