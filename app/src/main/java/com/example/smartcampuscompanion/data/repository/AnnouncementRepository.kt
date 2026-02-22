package com.example.smartcampuscompanion.data.repository

import com.example.smartcampuscompanion.data.local.AnnouncementDao
import com.example.smartcampuscompanion.data.local.AnnouncementEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class AnnouncementRepository(private val dao: AnnouncementDao) {

    val announcements: Flow<List<AnnouncementEntity>> = dao.getAllAnnouncements()

    suspend fun insert(announcement: AnnouncementEntity) {
        dao.insert(announcement)
    }

    suspend fun update(announcement: AnnouncementEntity) {
        dao.update(announcement)
    }

    suspend fun delete(announcement: AnnouncementEntity) {
        dao.delete(announcement)
    }

    // Only seeds data if the table is currently empty
    suspend fun insertIfEmpty(announcements: List<AnnouncementEntity>) {
        val current = dao.getAllAnnouncements().first()
        if (current.isEmpty()) {
            announcements.forEach { dao.insert(it) }
        }
    }
}