package com.example.smartcampuscompanion.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announcements")
data class AnnouncementEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val date: String,
    val category: String = "General",
    val isRead: Boolean = false,
    // Stores the Firestore document ID so we can sync read status back
    val firestoreId: String = ""
)
