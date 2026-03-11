package com.example.smartcampuscompanion

class AuthUtils {

    fun validateLogin(username: String, password: String): Boolean {
        return username == "admin" && password == "admin123"
    }

    fun isUsernameValid(username: String): Boolean {
        return username.isNotBlank() && username.length >= 3
    }

    fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank() && password.length >= 4
    }
}