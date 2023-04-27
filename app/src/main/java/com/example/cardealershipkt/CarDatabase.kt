package com.example.cardealershipkt

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CarItem::class], version = 2, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {

    abstract fun carDao(): CarDAO

    companion object{
        private var INSTANCE: CarDatabase? = null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "cars_database"
                ).build()
            }
        }

        fun get(): CarDatabase {
            return INSTANCE ?: throw IllegalStateException("CarRepository must be initialized")
        }
    }
}