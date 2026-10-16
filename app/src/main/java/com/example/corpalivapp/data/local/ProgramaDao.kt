package com.example.corpalivapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corpalivapp.data.model.Programa
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramaDao {

    @Query("SELECT * FROM programas ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<Programa>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(programa: Programa)

    @Delete
    suspend fun eliminar(programa: Programa)
}