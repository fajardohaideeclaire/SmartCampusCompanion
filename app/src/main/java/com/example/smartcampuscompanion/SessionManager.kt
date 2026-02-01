package com.example.smartcampuscompanion
import android.content.Context

class SessionManager {

    fun saveSession(context: Context) {
        val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("isLoggedIn", true).apply()
    }

}