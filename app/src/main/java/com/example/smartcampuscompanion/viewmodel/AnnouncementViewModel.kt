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

    init {
        seedSampleAnnouncements()
    }

    private fun seedSampleAnnouncements() {
        viewModelScope.launch {
            repository.insertIfEmpty(
                listOf(
                    AnnouncementEntity(
                        title = "Midterm Examinations Schedule",
                        content = "Midterm examinations will begin next week. Please check your respective college bulletin boards for the full schedule and room assignments.",
                        date = "2026-03-01",
                        category = "Academic"
                    ),
                    AnnouncementEntity(
                        title = "System Maintenance Advisory",
                        content = "The student portal will be unavailable this Saturday, 12MN–6AM, due to scheduled system maintenance. Please complete any online transactions before then.",
                        date = "2026-03-05",
                        category = "Advisory"
                    ),
                    AnnouncementEntity(
                        title = "Library Extended Hours",
                        content = "The campus library will be open 24/7 starting next week through the end of midterm examinations to support student review sessions.",
                        date = "2026-03-06",
                        category = "Facilities"
                    ),
                    AnnouncementEntity(
                        title = "Campus Foundation Day Celebration",
                        content = "Join us for our annual Foundation Day celebration on March 15. Activities include a program, cultural presentations, and a food fair. All students are encouraged to attend.",
                        date = "2026-03-08",
                        category = "Events"
                    ),
                    AnnouncementEntity(
                        title = "Scholarship Application Open",
                        content = "Applications for the Academic Excellence Scholarship for the second semester are now open. Submit your requirements to the Scholarship Office by March 20.",
                        date = "2026-03-09",
                        category = "Academic"
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
