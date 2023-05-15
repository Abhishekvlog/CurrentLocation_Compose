package com.example.currentlocation.data.local.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patient_table")
data class PatientEntity(
    val stateId: Int,
    val cityId: Int,
    val pinCodeId: Int,
    val address : String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
