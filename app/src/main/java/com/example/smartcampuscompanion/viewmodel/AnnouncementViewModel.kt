package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    // Auto-seed sample data when ViewModel is first created
    init {
        seedSampleAnnouncements()
    }

    private fun seedSampleAnnouncements() {
        viewModelScope.launch {
            // Only insert if the table is empty so we don't duplicate on every launch
            repository.insertIfEmpty(
                listOf(
                    AnnouncementEntity(
                        title = "Midterm Examinations",
                        content = "Midterm examinations will begin next week. Please check your schedules.",
                        date = "2026-03-01"
                    ),
                    AnnouncementEntity(
                        title = "System Maintenance",
                        content = "System maintenance is scheduled this weekend. Expect brief downtime.",
                        date = "2026-03-05"
                    ),
                    AnnouncementEntity(
                        title = "Library Hours Extended",
                        content = "The library will be open 24/7 during midterm week for student use.",
                        date = "2026-03-06"
                    )
                )
            )
        }
    }

    fun markAsRead(announcement: AnnouncementEntity) {
        viewModelScope.launch {
            repository.update(announcement.copy(isRead = true))
        }
    }
}

class AnnouncementViewModelFactory(
    private val repository: AnnouncementRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnnouncementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnnouncementViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}