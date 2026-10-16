package com.example.corpalivapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "programas")
data class Programa(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val activo: Boolean = true
)