package com.android.educo.offline

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * Author: A. L. Zailani
 */
@Dao
interface OfflineDao {
    @Query("SELECT * FROM offline_materials_table ORDER BY time_created DESC")
    fun all(): LiveData<List<Offline>>

    @Query("SELECT * from offline_materials_table WHERE material_type='Audio' ORDER BY time_created DESC")
    fun audios(): LiveData<List<Offline>>

    @Query("SELECT * from offline_materials_table WHERE material_type='Document' ORDER BY time_created DESC")
    fun documents(): LiveData<List<Offline>>

    @Query("SELECT * from offline_materials_table WHERE material_type='Video' ORDER BY time_created DESC")
    fun videos(): LiveData<List<Offline>>

    @Query("SELECT * FROM offline_materials_table WHERE id=:record_id")
    fun get(record_id: String): LiveData<Offline>

    @Query("DELETE FROM offline_materials_table")
    fun clear()

    @Insert
    suspend fun insert(offline: Offline)

    @Delete
    suspend fun clear(offline: Offline)
}