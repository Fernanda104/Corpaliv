package com.example.corpalivapp.data.repository


import com.example.corpalivapp.data.local.ProductoDao
import com.example.corpalivapp.data.model.Producto
import kotlinx.coroutines.flow.Flow

class ProductoRepository(
    private val productoDao: ProductoDao
) {
    fun obtenerTodos(): Flow<List<Producto>> {
        return productoDao.obtenerTodos()
    }

    suspend fun insertar(producto: Producto) {
        productoDao.insertar(producto)
    }

    suspend fun eliminar(producto: Producto) {
        productoDao.eliminar(producto)
    }
}