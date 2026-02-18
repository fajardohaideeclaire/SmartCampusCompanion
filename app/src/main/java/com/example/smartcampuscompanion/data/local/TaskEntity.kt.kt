package com.example.smartcampuscompanion.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class `TaskEntity.kt`(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dateTime: String,
    val isCompleted: Boolean = false
)
