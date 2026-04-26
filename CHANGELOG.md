# Changelog

All notable changes to Smart Campus Companion are documented here.

---

## v1.0-midterm - 2026-03-11

### Added
- Task Manager (add, edit, delete tasks with date & time picker)
- Task completion checkbox with strikethrough styling
- Delete confirmation dialog for tasks
- Announcements module with Room Database
- AnnouncementEntity with category field (Academic, Advisory, Facilities, Events)
- AnnouncementRepository and AnnouncementViewModel with ViewModelFactory
- Auto-seeding sample announcements on first launch (insertIfEmpty)
- Unread/read section separation in AnnouncementScreen
- Category tag labels on announcement cards
- Unread dot indicator on announcement cards
- Campus Information screen with expandable college cards
- All 7 colleges with programs, majors, dean, contact info (CAS, CBAA, CCS, COED, COE, CHAS, Graduate School)
- Facilities section (Library, Clinic, Registrar)
- Room Database persistence for tasks and announcements
- MVVM architecture with ViewModels, Factories, and Repositories
- SessionManager for persistent login session (stays logged in on relaunch)
- AuthUtils for credential validation
- Shared AnnouncementViewModel across Dashboard and AnnouncementScreen for live unread badge sync
- Unread count badge on notification bell and Announcements tile in Dashboard
- Back navigation for all secondary screens
- Empty state messages for Tasks and Announcements
- Gradient header on TaskScreen, AnnouncementScreen, and CampusInfoScreen
- Dynamic greeting (Good Morning / Afternoon / Evening) on Dashboard
- Real username displayed from SessionManager on Dashboard
- Today's date shown in Dashboard header
- Logout button on Dashboard header

### Changed
- DashboardScreen redesigned with centered tile cards, compact header, cleaner layout
- TaskScreen improved with gradient header, styled form card, due date chip, icon buttons for edit/delete
- AnnouncementScreen header changed to plain Column (no overlapping decorative circles)
- CampusInfoScreen expanded to 7 colleges with full program listings and majors
- AppNav updated with shared AnnouncementViewModel, session check for start destination, remember blocks for repositories
- LoginScreen hint text removed
- Login credentials changed to student / 1234
- SessionManager upgraded with isLoggedIn(), getUsername(), clearSession()
- AuthUtils updated with isUsernameValid() and isPasswordValid() helpers
- Background color standardized to #F6F8F7 across all screens

### Fixed
- ViewModel recreation on recomposition causing state loss (fixed with ViewModelFactory)
- Announcements not appearing due to missing seed call (moved to init block)
- Incorrect time formatting in tasks (now zero-padded e.g. 09:05)
- Login navigating without credential check
- SessionManager not accepting context in constructor
- Decorative circles appearing behind notification bell icon in Dashboard header
- AppNav viewModel() called outside composable scope (fixed with remember blocks)

---

## v0.1-initial - 2026-02-01

### Added
- Initial Android project setup
- LoginScreen UI
- Dashboard placeholder
- Navigation using NavHost
- App theme setup (DarkGreen, MediumGreen, PaleGreen)

# Changelog - v2.0-final

### Added
- Firebase Firestore implementation for database-driven role management.
- Admin Panel with in-place Edit (Popup) and Delete functionality.
- Sign-Up screen with role selection and Admin Key verification.
- Global Dark Mode support using dynamic Material3 color schemes.
- Firebase Cloud Messaging (FCM) for real-time push notifications.
- High-fidelity visual refinements (Layered headers, weight-based layouts).

### Changed
- Migrated from hardcoded email checks to cloud-based profile fetching.
- Redesigned all 7 screens for UI/UX consistency and professional spacing.
- Integrated real-time data counts into the Student Dashboard.

### Fixed
- Fixed text clipping on management cards and dashboard tiles.
- Corrected overlapping scrolling behavior on Info and Task screens.
- Standardized error handling and loading states for all network actions.
