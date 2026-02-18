package com.example.smartcampuscompanion.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {

    @Query("SELECT * FROM announcements ORDER BY id DESC")
    fun getAllAnnouncements(): Flow<List<AnnouncementEntity>>

    @Insert
    suspend fun insert(announcement: AnnouncementEntity)

    @Update
    suspend fun update(announcement: AnnouncementEntity)

    @Delete
    suspend fun delete(announcement: AnnouncementEntity)
}