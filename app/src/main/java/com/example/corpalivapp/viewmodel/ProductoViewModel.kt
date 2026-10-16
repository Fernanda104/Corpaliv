package com.example.corpalivapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.corpalivapp.data.model.Producto
import com.example.corpalivapp.data.repository.ProductoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductoViewModel(
    private val repository: ProductoRepository
) : ViewModel() {

    val productos = repository.obtenerTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun insertar(producto: Producto) {
        viewModelScope.launch {
            repository.insertar(producto)
        }
    }

    fun eliminar(producto: Producto) {
        viewModelScope.launch {
            repository.eliminar(producto)
        }
    }
}