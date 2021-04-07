package com.example.reportcity.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reportcity.entities.Notas
import kotlinx.coroutines.flow.Flow

@Dao
interface NotasDao {

    @Query("SELECT * FROM notas ORDER BY title ASC")
    fun getNotas(): Flow<List<Notas>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(notas: Notas)

    @Query("DELETE FROM notas WHERE id == :id ")
    suspend fun deleteNota(id: Int)

    @Query("DELETE FROM notas")
    suspend fun deleteAll()

}