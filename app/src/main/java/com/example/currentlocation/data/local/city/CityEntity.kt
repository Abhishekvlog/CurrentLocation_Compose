package com.example.currentlocation.data.local.city

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "city_table", indices = [Index(value = ["city"], unique = true)])
data class CityEntity(
    @ColumnInfo(name = "city")
    val city: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)