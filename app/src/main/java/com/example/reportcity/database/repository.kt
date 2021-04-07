package com.example.reportcity.database


import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.reportcity.dao.NotasDao
import com.example.reportcity.entities.Notas
import kotlinx.coroutines.flow.Flow

class repository(private val notasDao: NotasDao) {

    val allNotas: Flow<List<Notas>> = notasDao.getNotas()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(notas: Notas) {
        notasDao.insert(notas)
    }

    suspend fun deleteOne(id: Int){
        notasDao.deleteNota(id)
    }

    suspend fun deleteAll(){
        notasDao.deleteAll()
    }


}