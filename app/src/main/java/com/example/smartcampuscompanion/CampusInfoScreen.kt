package com.example.smartcampuscompanion

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.Card
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding

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
                Card(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = department,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}