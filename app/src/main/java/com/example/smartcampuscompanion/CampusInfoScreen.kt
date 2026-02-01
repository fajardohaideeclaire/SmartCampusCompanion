package com.example.smartcampuscompanion

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class CampusInfoScreen {

    @Composable
    fun CampusInfoScreen() {
        val departments = listOf(
            "Computer Science\nEmail: cs@campus.edu",
            "Engineering\nEmail: eng@campus.edu",
            "Business\nEmail: biz@campus.edu"
        )

        LazyColumn {
            items(departments) { department ->
                Text(text = department)
            }
        }
    }

}