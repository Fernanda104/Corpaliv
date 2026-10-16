package com.example.corpalivapp.data.model

data class Producto(
    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val disponible: Boolean = true
)