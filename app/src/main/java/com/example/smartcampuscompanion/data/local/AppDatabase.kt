package com.example.smartcampuscompanion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smartcampuscompanion.data.local.TaskEntity
import com.example.smartcampuscompanion.data.local.AnnouncementEntity
import com.example.smartcampuscompanion.data.local.TaskDao
import com.example.smartcampuscompanion.data.local.AnnouncementDao


@Database(
    entities = [
        TaskEntity::class,
        AnnouncementEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao
    abstract fun announcementDao(): AnnouncementDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "smart_campus_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
