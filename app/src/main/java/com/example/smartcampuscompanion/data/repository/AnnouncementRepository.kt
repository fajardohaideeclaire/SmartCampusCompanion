package com.example.smartcampuscompanion.data.repository

import com.example.smartcampuscompanion.data.local.AnnouncementDao
import com.example.smartcampuscompanion.data.local.AnnouncementEntity
import com.example.smartcampuscompanion.firebase.FirebaseService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach

class AnnouncementRepository(
    private val dao: AnnouncementDao,
    private val firebaseService: FirebaseService = FirebaseService()
) {

    // Primary source: Firestore real-time flow
    // Each emission syncs to Room as local cache
    val announcements: Flow<List<AnnouncementEntity>> =
        firebaseService.getAnnouncementsFlow()
            .onEach { firestoreList ->
                // Sync Firestore data into Room cache
                if (firestoreList.isNotEmpty()) {
                    syncToRoom(firestoreList)
                }
            }

    // Fallback: local Room data (used if Firestore is unreachable)
    val localAnnouncements: Flow<List<AnnouncementEntity>> =
        dao.getAllAnnouncements()

    private suspend fun syncToRoom(firestoreList: List<AnnouncementEntity>) {
        val existing = dao.getAllAnnouncements().first()
        val existingFirestoreIds = existing.map { it.firestoreId }.toSet()

        firestoreList.forEach { firestoreItem ->
            if (firestoreItem.firestoreId !in existingFirestoreIds) {
                // New announcement: Add to local DB (defaults to isRead = false)
                dao.insert(firestoreItem.copy(isRead = false))
            } else {
                // Existing announcement: Update content but KEEP the local isRead status
                val local = existing.find { it.firestoreId == firestoreItem.firestoreId }
                if (local != null) {
                    dao.update(firestoreItem.copy(
                        id = local.id, 
                        isRead = local.isRead // This is the key fix
                    ))
                }
            }
        }
    }

    suspend fun insert(announcement: AnnouncementEntity) {
        dao.insert(announcement)
    }

    suspend fun update(announcement: AnnouncementEntity) {
        // Only update the local Room database
        dao.update(announcement)
    }

    suspend fun updateAnnouncement(
        firestoreId: String,
        title: String,
        content: String,
        category: String
    ): Boolean {
        return firebaseService.updateAnnouncement(firestoreId, title, content, category)
    }

    suspend fun delete(announcement: AnnouncementEntity) {
        dao.delete(announcement)
        // Also delete from Firestore if it exists there
        if (announcement.firestoreId.isNotEmpty()) {
            firebaseService.deleteAnnouncement(announcement.firestoreId)
        }
    }

    // Post announcement to Firestore (admin action)
    suspend fun postAnnouncement(
        title: String,
        content: String,
        category: String,
        postedBy: String
    ): Boolean {
        return firebaseService.postAnnouncement(title, content, category, postedBy)
    }

    // Local seed only if Room is empty (fallback for offline)
    suspend fun insertIfEmpty(announcements: List<AnnouncementEntity>) {
        val current = dao.getAllAnnouncements().first()
        if (current.isEmpty()) {
            announcements.forEach { dao.insert(it) }
        }
    }
}
