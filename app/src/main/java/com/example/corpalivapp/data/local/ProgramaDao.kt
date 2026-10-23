
package com.example.corpalivapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.corpalivapp.data.model.Programa
import kotlinx.coroutines.flow.Flow

@Dao
interface ProgramaDao {

    @Query("SELECT * FROM programas ORDER BY nombre ASC")
    fun obtenerTodos(): Flow<List<Programa>>

    @Query("SELECT * FROM programas WHERE nombre = :nombre LIMIT 1")
    suspend fun obtenerPorNombre(nombre: String): Programa?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(programa: Programa)

    @Update
    suspend fun actualizar(programa: Programa)

    @Delete
    suspend fun eliminar(programa: Programa)
}
