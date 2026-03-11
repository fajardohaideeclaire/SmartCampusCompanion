package com.example.smartcampuscompanion

import android.content.Context

class SessionManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun saveSession(username: String) {
        prefs.edit()
            .putBoolean("isLoggedIn", true)
            .putString("username", username)
            .apply()
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean("isLoggedIn", false)
    }

    fun getUsername(): String {
        return prefs.getString("username", "Student") ?: "Student"
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}