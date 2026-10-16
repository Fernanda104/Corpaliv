package com.example.corpalivapp.data.repository


import com.example.corpalivapp.data.local.ColaboracionDao
import com.example.corpalivapp.data.model.Colaboracion
import kotlinx.coroutines.flow.Flow

class ColaboracionRepository(
    private val colaboracionDao: ColaboracionDao
) {
    fun obtenerTodas(): Flow<List<Colaboracion>> {
        return colaboracionDao.obtenerTodas()
    }

    suspend fun insertar(colaboracion: Colaboracion) {
        colaboracionDao.insertar(colaboracion)
    }

    suspend fun eliminar(colaboracion: Colaboracion) {
        colaboracionDao.eliminar(colaboracion)
    }
}