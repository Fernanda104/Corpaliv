package com.example.corpalivapp.data.model

data class Colaboracion(
    val id: Int = 0,
    val nombreContacto: String,
    val organizacion: String,
    val correo: String,
    val tipoColaboracion: String,
    val cantidadPersonas: Int,
    val fechaContacto: String,
    val aceptaContacto: Boolean
)