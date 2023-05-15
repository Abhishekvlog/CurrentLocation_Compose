package com.example.currentlocation.data.local.state

import androidx.room.*

@Dao
interface StateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertState(stateEntity: StateEntity)

    @Query("SELECT * FROM state_table")
    suspend fun getState(): List<StateEntity>

    @Query("SELECT * FROM state_table where state = :state")
    suspend fun getStateId(state : String) : StateEntity
}