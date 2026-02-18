package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.local.AnnouncementEntity
import com.example.smartcampuscompanion.data.repository.AnnouncementRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val repository: AnnouncementRepository
) : ViewModel() {

    val announcements = repository.announcements
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun insertSampleAnnouncements() {
        viewModelScope.launch {
            repository.insert(
                AnnouncementEntity(
                    title = "Midterm Announcement",
                    content = "Midterm examinations will begin next week.",
                    date = "2026-03-01"
                )
            )

            repository.insert(
                AnnouncementEntity(
                    title = "System Maintenance",
                    content = "System maintenance scheduled this weekend.",
                    date = "2026-03-05"
                )
            )
        }
    }


    fun markAsRead(announcement: AnnouncementEntity) {
        viewModelScope.launch {
            repository.update(
                announcement.copy(isRead = true)
            )
        }
    }
}
