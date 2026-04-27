package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.local.AnnouncementEntity
import com.example.smartcampuscompanion.data.repository.AnnouncementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val repository: AnnouncementRepository
) : ViewModel() {

    // Firestore real-time announcements (with Room cache fallback)
    val announcements = repository.announcements
        .catch { emit(emptyList()) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _postSuccess = MutableStateFlow(false)
    val postSuccess: StateFlow<Boolean> = _postSuccess

    private val _editingAnnouncement = MutableStateFlow<AnnouncementEntity?>(null)
    val editingAnnouncement: StateFlow<AnnouncementEntity?> = _editingAnnouncement

    init {
        seedLocalFallback()
    }

    // Only seeds Room if completely empty — fallback for offline use
    private fun seedLocalFallback() {
        viewModelScope.launch {
            repository.insertIfEmpty(
                listOf(
                    AnnouncementEntity(
                        title = "Welcome to Smart Campus",
                        content = "This is a local fallback announcement. Connect to the internet to see live announcements from Firestore.",
                        date = "2026-04-01",
                        category = "General"
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

    fun deleteAnnouncement(announcement: AnnouncementEntity) {
        viewModelScope.launch {
            repository.delete(announcement)
        }
    }

    fun setEditingAnnouncement(announcement: AnnouncementEntity?) {
        _editingAnnouncement.value = announcement
    }

    // Admin updates an existing announcement in Firestore
    fun updateAnnouncement(
        firestoreId: String,
        title: String,
        content: String,
        category: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _postSuccess.value = false

            val success = repository.updateAnnouncement(
                firestoreId = firestoreId,
                title = title,
                content = content,
                category = category
            )

            if (success) {
                _postSuccess.value = true
                _editingAnnouncement.value = null
            } else {
                _errorMessage.value = "Failed to update announcement. Check your connection."
            }
            _isLoading.value = false
        }
    }

    // Admin posts a new announcement to Firestore
    fun postAnnouncement(
        title: String,
        content: String,
        category: String,
        postedBy: String
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _postSuccess.value = false

            val success = repository.postAnnouncement(
                title = title,
                content = content,
                category = category,
                postedBy = postedBy
            )

            if (success) {
                _postSuccess.value = true
            } else {
                _errorMessage.value = "Failed to post announcement. Check your connection."
            }
            _isLoading.value = false
        }
    }

    fun clearError() { _errorMessage.value = null }
    fun clearPostSuccess() { _postSuccess.value = false }
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
