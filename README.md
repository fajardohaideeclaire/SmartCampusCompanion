# Smart Campus Companion (Finals Release)

A high-fidelity Android application for campus life management, built with Jetpack Compose and Firebase.

**Version:** v2.0-final

## Key Features

### 🔐 Advanced Authentication
- **Multi-user Sign Up:** Distinct flows for Students and Administrators.
- **Role Verification:** Secure Admin registration via secret key (`CAMPUS_ADMIN`).
- **Cloud Profiles:** Roles and user data managed via Firestore (No more hardcoding).

### 📢 Broadcast System (Admin)
- **Control Center:** One-page dashboard for campus-wide management.
- **Live Editing:** Modify existing announcements via high-fidelity popups.
- **Secure Deletion:** Remove broadcasts with real-time cloud synchronization.

### 📱 Student Hub
- **Real-time Stats:** Live count of pending tasks and unread updates.
- **Announcement Feed:** Categorized unread/read updates with cloud-sync status.
- **Task Manager:** Full CRUD task list with Room persistence.
- **Campus Info:** Expandable directory of 7 colleges and campus facilities.

### 🎨 Premium UI/UX
- **Layered Design:** Sophisticated gradient headers and floating cards.
- **Global Dark Mode:** Full theme support across the entire application.
- **Push Notifications:** Real-time FCM integration for background alerts.

## Setup Instructions
1. Place `google-services.json` in the `app/` directory.
2. Ensure Firebase Auth, Firestore, and Messaging are enabled in the console.
3. Secret Admin Key: `CAMPUS_ADMIN`
