package com.example.smartcampuscompanion

import android.content.Context
import com.google.firebase.auth.FirebaseAuth

class SessionManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
    private val auth = FirebaseAuth.getInstance()

    fun saveSession(username: String, role: UserRole) {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putString("username", username)
            .putString("role", role.name)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        // Check both SharedPreferences and Firebase Auth
        return prefs.getBoolean("isLoggedIn", false) &&
                auth.currentUser != null
    }

    fun getUsername(): String =
        prefs.getString("username", "Student") ?: "Student"

    fun getRole(): UserRole {
        val roleName = prefs.getString("role", UserRole.STUDENT.name)
            ?: UserRole.STUDENT.name
        return try {
            UserRole.valueOf(roleName)
        } catch (e: IllegalArgumentException) {
            UserRole.STUDENT
        }
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}
