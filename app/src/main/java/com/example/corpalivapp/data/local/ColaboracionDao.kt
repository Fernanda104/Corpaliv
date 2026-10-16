package com.example.corpalivapp.data.local


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.corpalivapp.data.model.Colaboracion
import kotlinx.coroutines.flow.Flow

@Dao
interface ColaboracionDao {

    @Query("SELECT * FROM colaboraciones ORDER BY id DESC")
    fun obtenerTodas(): Flow<List<Colaboracion>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(colaboracion: Colaboracion)

    @Delete
    suspend fun eliminar(colaboracion: Colaboracion)
}