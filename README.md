# Smart Campus Companion

Smart Campus Companion is a mobile application developed for university students.
It provides easy access to campus-related information such as announcements,
campus facilities, and academic resources through a simple and user-friendly interface.

The application is built using modern Android development practices including
Jetpack Compose, MVVM architecture, Room Database, and Git-based collaboration.

This project is developed as part of the Android Development Case Study.


## Team Roles

- Fajardo – Team Leader / System Integration
- De Leon – Git Manager
- Fernandez – UI/UX Developer
- Dacillo – Feature Developer
- Del Mundo – QA / Documenter


## Key Features

- Campus announcements and information
- Task Manager for organizing student tasks
- Login and session management
- Simple and responsive Jetpack Compose UI
- Local data persistence using Room Database


## Architecture

The application follows the **MVVM (Model–View–ViewModel)** architecture pattern.

Layers used in the system:

UI Layer  
Jetpack Compose screens responsible for displaying data and handling user interaction.

ViewModel Layer  
Manages UI state and business logic using StateFlow.

Repository Layer  
Acts as the mediator between the ViewModel and the data sources.

Data Layer  
Uses Room Database with DAOs and Entities for local data storage.

Data Flow:

UI → ViewModel → Repository → Room Database


## Git Workflow

The team follows a **feature branch workflow** using GitHub.

- The project was initialized using Android Studio.
- A GitHub repository was created for version control.
- Each team member works on an assigned branch.
- Changes are submitted through Pull Requests.
- Pull Requests are reviewed before merging into the **develop** branch.
- The **develop** branch serves as the integration branch for completed features.
- Stable versions are tagged as releases.


## Branches Used

- main – Stable release version of the application
- develop – Integration and testing branch

Feature branches:
- teamleader-fajardo – Architecture setup and system integration
- feature/task-manager – Implementation of the Task Manager module
- feature/announcements – Implementation of the Campus Announcements module
- ui/ux-fernandez – UI design and layout improvements
- git-deLeon – Repository configuration and Git workflow management
- qa-delmundo – Testing, documentation, and project reports

## Midterm Release

The midterm milestone of the project is tagged as:

v1.0-midterm

This release includes:
- Initial MVVM architecture implementation
- Room database setup
- Task Manager module
- Campus announcements module
- Basic navigation and UI structure