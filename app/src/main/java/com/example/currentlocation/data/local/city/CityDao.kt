package com.example.currentlocation.data.local.city

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCity(cityEntity: CityEntity)

    @Query("SELECT * FROM city_table")
    suspend fun getCity(): List<CityEntity>

    @Query("SELECT * FROM city_table where city = :city")
    suspend fun getCityId(city : String) : CityEntity
}