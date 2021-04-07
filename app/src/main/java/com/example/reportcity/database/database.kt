package com.example.reportcity.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.reportcity.entities.Notas
import com.example.reportcity.dao.NotasDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [Notas::class], version = 1)
public abstract class reportyCityDatabase : RoomDatabase() {

    abstract fun NotasDao(): NotasDao

    private class reportyCityDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var NotasDao = database.NotasDao()

                    // Delete all content here.

                    //NotasDao.deleteAll()


                    var nota1 = Notas(1, "Titulo", "sfgijsfasd d auSDN Ads ", "adsasasd")
                    NotasDao.insert(nota1)
                    var nota2 = Notas(2, "Titulo", "sfgijsfasd d auSDN Ads ", "adsasasd")
                    NotasDao.insert(nota2)


                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: reportyCityDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): reportyCityDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    reportyCityDatabase::class.java,
                    "reporty_city"
                )
                .addCallback(reportyCityDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}