package com.example.smartcampuscompanion.firebase

import com.example.smartcampuscompanion.data.local.AnnouncementEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FirebaseService {

    private val db = FirebaseFirestore.getInstance()
    private val announcementsCollection = db.collection("announcements")

    // Real-time listener — emits a new list every time Firestore changes
    fun getAnnouncementsFlow(): Flow<List<AnnouncementEntity>> = callbackFlow {
        val listener = announcementsCollection
            .orderBy("date", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) {
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snapshot.documents.mapNotNull { doc ->
                    try {
                        AnnouncementEntity(
                            id = 0,
                            title = doc.getString("title") ?: "",
                            content = doc.getString("content") ?: "",
                            date = doc.getString("date") ?: "",
                            category = doc.getString("category") ?: "General",
                            isRead = doc.getBoolean("isRead") ?: false,
                            firestoreId = doc.id
                        )
                    } catch (e: Exception) {
                        null
                    }
                }
                trySend(list)
            }
        awaitClose { listener.remove() }
    }

    // Post a new announcement to Firestore
    suspend fun postAnnouncement(
        title: String,
        content: String,
        category: String,
        postedBy: String
    ): Boolean {
        return try {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            announcementsCollection.add(
                mapOf(
                    "title"    to title,
                    "content"  to content,
                    "date"     to today,
                    "category" to category,
                    "isRead"   to false,
                    "postedBy" to postedBy
                )
            ).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // Update an existing announcement (admin only)
    suspend fun updateAnnouncement(
        firestoreId: String,
        title: String,
        content: String,
        category: String
    ): Boolean {
        return try {
            if (firestoreId.isNotEmpty()) {
                announcementsCollection.document(firestoreId)
                    .update(
                        mapOf(
                            "title" to title,
                            "content" to content,
                            "category" to category
                        )
                    ).await()
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }

    // Mark announcement as read in Firestore
    suspend fun markAsRead(firestoreId: String) {
        try {
            if (firestoreId.isNotEmpty()) {
                announcementsCollection.document(firestoreId)
                    .update("isRead", true)
                    .await()
            }
        } catch (e: Exception) {
            // Silently fail
        }
    }

    // Delete announcement from Firestore (admin only)
    suspend fun deleteAnnouncement(firestoreId: String): Boolean {
        return try {
            if (firestoreId.isNotEmpty()) {
                announcementsCollection.document(firestoreId).delete().await()
                true
            } else false
        } catch (e: Exception) {
            false
        }
    }
}
