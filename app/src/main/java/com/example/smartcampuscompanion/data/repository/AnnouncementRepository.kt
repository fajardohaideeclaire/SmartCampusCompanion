package com.example.smartcampuscompanion.data.repository

import com.example.smartcampuscompanion.data.local.AnnouncementDao
import com.example.smartcampuscompanion.data.local.AnnouncementEntity

class AnnouncementRepository(private val dao: AnnouncementDao) {

    val announcements = dao.getAllAnnouncements()

    suspend fun insert(announcement: AnnouncementEntity) {
        dao.insert(announcement)
    }

    suspend fun update(announcement: AnnouncementEntity) {
        dao.update(announcement)
    }

    suspend fun delete(announcement: AnnouncementEntity) {
        dao.delete(announcement)
    }
}
