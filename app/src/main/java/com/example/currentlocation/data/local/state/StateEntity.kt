package com.example.currentlocation.data.local.state

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "state_table", indices = [Index(value = ["state"], unique = true)])
data class StateEntity(
    @ColumnInfo(name = "state")
    val state: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
