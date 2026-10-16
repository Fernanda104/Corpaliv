package com.example.corpalivapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpalivapp.data.model.Colaboracion
import com.example.corpalivapp.data.repository.ColaboracionRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ColaboracionViewModel(
    private val repository: ColaboracionRepository
) : ViewModel() {

    val colaboraciones = repository.obtenerTodas()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertar(colaboracion: Colaboracion) {
        viewModelScope.launch {
            repository.insertar(colaboracion)
        }
    }

    fun eliminar(colaboracion: Colaboracion) {
        viewModelScope.launch {
            repository.eliminar(colaboracion)
        }
    }
}