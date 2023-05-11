package com.example.currentlocation.data.local

import androidx.room.*

@Dao
interface StateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertState(stateEntity: StateEntity)

    @Query("SELECT * FROM state_table")
    suspend fun getState(): List<StateEntity>
}