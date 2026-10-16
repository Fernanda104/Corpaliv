package com.example.corpalivapp.data.repository

import com.example.corpalivapp.data.local.ProgramaDao
import com.example.corpalivapp.data.model.Programa
import kotlinx.coroutines.flow.Flow

class ProgramaRepository(
    private val programaDao: ProgramaDao
) {
    fun obtenerTodos(): Flow<List<Programa>> {
        return programaDao.obtenerTodos()
    }

    suspend fun insertar(programa: Programa) {
        programaDao.insertar(programa)
    }

    suspend fun eliminar(programa: Programa) {
        programaDao.eliminar(programa)
    }
}