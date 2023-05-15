package com.example.currentlocation.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.currentlocation.data.local.city.CityDao
import com.example.currentlocation.data.local.city.CityEntity
import com.example.currentlocation.data.local.patient.PatientDao
import com.example.currentlocation.data.local.patient.PatientEntity
import com.example.currentlocation.data.local.state.StateDao
import com.example.currentlocation.data.local.state.StateEntity

@Database(entities = [StateEntity::class, CityEntity::class, PatientEntity::class], version = 1)
abstract class StateDatabase : RoomDatabase() {
    abstract val stateDao: StateDao
    abstract val cityDao: CityDao
    abstract val patientDao: PatientDao
}