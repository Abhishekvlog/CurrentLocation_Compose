package com.example.currentlocation.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [StateEntity::class], version = 1)
abstract class StateDatabase : RoomDatabase() {
    abstract val dao: StateDao
//    companion object {
//        private var instance: StateDatabase? = null
//        fun getSessionDatabase(context: Context): StateDatabase {
//            if (instance != null) {
//                return instance!!
//            } else {
//                val builder = Room.databaseBuilder(
//                    context.applicationContext,
//                    StateDatabase::class.java,
//                    "state_db"
//                )
//                builder.fallbackToDestructiveMigration()
//                instance = builder.build()
//            }
//            return instance!!
//        }
//    }
}