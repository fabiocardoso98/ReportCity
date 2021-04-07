package com.example.reportcity.viewModel

import androidx.lifecycle.*
import com.example.reportcity.database.repository
import com.example.reportcity.entities.Notas
import kotlinx.coroutines.launch

class notaViewModel(private val repository: repository) : ViewModel() {
    val allNotas: LiveData<List<Notas>> = repository.allNotas.asLiveData()


    fun insert(notas: Notas) = viewModelScope.launch {
        repository.insert(notas)
    }

    fun deleteNotasOne(id: Int) = viewModelScope.launch {
        repository.deleteOne(id)
    }

    fun deleteNotasAll() = viewModelScope.launch {
        repository.deleteAll()
    }
}

class notaViewModelFactory(private val repository: repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(notaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return notaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
