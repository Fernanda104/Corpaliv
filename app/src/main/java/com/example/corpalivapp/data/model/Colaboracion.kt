package com.example.corpalivapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "colaboraciones")
data class Colaboracion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombreContacto: String,
    val organizacion: String,
    val correo: String,
    val tipoColaboracion: String,
    val cantidadPersonas: Int,
    val fechaContacto: String,
    val aceptaContacto: Boolean
)