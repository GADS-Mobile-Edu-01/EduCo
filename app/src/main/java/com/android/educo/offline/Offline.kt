package com.android.educo.offline

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Author: A. L. Zailani
 */
@Entity(tableName = "offline_materials_table")
data class Offline(
    @PrimaryKey
    var id: String = "",

    @ColumnInfo(name = "material_type")
    var type: String = "",

    @ColumnInfo(name = "material_title")
    var title: String = "",

    @ColumnInfo(name = "material_description")
    var description: String = "",

    @ColumnInfo(name = "material_local_storage_path")
    var downloadLink: String = "",

    @ColumnInfo(name = "time_created")
    var timeCreated: String = ""
)