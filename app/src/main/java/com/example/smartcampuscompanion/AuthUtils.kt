package com.example.smartcampuscompanion

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthUtils {

    private val auth = FirebaseAuth.getInstance()

    // Returns UserRole on success, null on failure
    suspend fun login(email: String, password: String): UserRole? {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            when (email.lowercase()) {
                "admin@campus.edu" -> UserRole.ADMIN
                else               -> UserRole.STUDENT
            }
        } catch (e: Exception) {
            null
        }
    }

    fun isEmailValid(email: String): Boolean =
        email.isNotBlank() && email.contains("@")

    fun isPasswordValid(password: String): Boolean =
        password.isNotBlank() && password.length >= 4

    fun getCurrentUserEmail(): String? =
        auth.currentUser?.email

    fun signOut() {
        auth.signOut()
    }
}
