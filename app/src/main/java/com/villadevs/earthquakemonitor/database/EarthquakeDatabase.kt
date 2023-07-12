package com.villadevs.earthquakemonitor.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.villadevs.earthquakemonitor.model.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EarthquakeDatabase : RoomDatabase() {

    abstract fun earthquakeDao(): EarthquakeDao

    companion object {
        @Volatile
        private var INSTANCE: EarthquakeDatabase? = null

        fun getDatabase(context: Context): EarthquakeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    EarthquakeDatabase::class.java,
                    "earthquake_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }
}