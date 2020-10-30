package com.android.educo.offline

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Author: A. L. Zailani
 */
@Database(entities = [Offline::class], version = 1, exportSchema = false)
abstract class OfflineRoomDatabase : RoomDatabase() {
    abstract val offlineDao: OfflineDao

    companion object {
        @Volatile
        private var INSTANCE: OfflineRoomDatabase? = null

        fun getInstance(context: Context): OfflineRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OfflineRoomDatabase::class.java,
                        "educo_offline_materials_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}