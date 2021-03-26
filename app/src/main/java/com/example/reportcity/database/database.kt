package com.example.reportcity.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.reportcity.entities.Notas
import com.example.reportcity.dao.NotasDao

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = arrayOf(Notas::class), version = 1, exportSchema = false)
public abstract class reportyCityDatabase : RoomDatabase() {

    abstract fun NotasDao(): NotasDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: reportyCityDatabase? = null

        fun getDatabase(context: Context): reportyCityDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    reportyCityDatabase::class.java,
                    "reporty_city"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}