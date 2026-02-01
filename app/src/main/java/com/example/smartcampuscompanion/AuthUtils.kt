package com.example.smartcampuscompanion

class AuthUtils {

    fun validateLogin(username: String, password: String): Boolean {
        return username == "student" && password == "1234"
    }

}