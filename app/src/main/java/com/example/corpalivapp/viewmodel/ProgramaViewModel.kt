package com.example.corpalivapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpalivapp.data.model.Programa
import com.example.corpalivapp.data.repository.ProgramaRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProgramaViewModel(
    private val repository: ProgramaRepository
) : ViewModel() {

    val programas = repository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertar(programa: Programa) {
        viewModelScope.launch {
            repository.insertar(programa)
        }
    }

    fun eliminar(programa: Programa) {
        viewModelScope.launch {
            repository.eliminar(programa)
        }
    }
}