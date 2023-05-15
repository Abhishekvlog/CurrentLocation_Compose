package com.example.currentlocation.data.local.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PatientDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatientDetail(patientEntity: PatientEntity)

    @Query("SELECT * FROM PATIENT_TABLE")
    suspend fun getAllPatientData() : List<PatientEntity>


}