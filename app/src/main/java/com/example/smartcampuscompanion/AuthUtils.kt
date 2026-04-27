package com.example.smartcampuscompanion

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthUtils {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    // Returns UserRole on success, null on failure
    suspend fun login(email: String, password: String): UserRole? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return null
            
            // Fetch role from Firestore
            getUserRole(uid)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun signUp(email: String, password: String, role: UserRole): Boolean {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: return false
            
            // Save user profile to Firestore
            db.collection("users").document(uid).set(
                mapOf(
                    "email" to email,
                    "role" to role.name
                )
            ).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUserRole(uid: String): UserRole {
        return try {
            val doc = db.collection("users").document(uid).get().await()
            val roleName = doc.getString("role") ?: UserRole.STUDENT.name
            UserRole.valueOf(roleName)
        } catch (e: Exception) {
            UserRole.STUDENT
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
