package com.example.corpalivapp.viewmodel

// Aca juntamos en un solo objeto los valores, los errores y el aviso de éxito.
// Todo lo que la pantalla necesita para dibujar el formulario.
data class FormularioColaboracionEstado(
    // Lo que escribió la persona
    val nombre: String = "",
    val organizacion: String = "",
    val correo: String = "",
    val tipo: String = "",
    val cantidadPersonas: String = "",
    val fecha: String = "",
    val aceptaContacto: Boolean = false,

    // Error de cada campo (null = sin error)
    val errorNombre: String? = null,
    val errorOrganizacion: String? = null,
    val errorCorreo: String? = null,
    val errorTipo: String? = null,
    val errorCantidad: String? = null,
    val errorFecha: String? = null,
    val errorAceptaContacto: String? = null,

    // true justo después de guardar, para mostrar la confirmación
    val guardadoConExito: Boolean = false,

    // Datos de la última solicitud guardada, para el texto de la notificación
    val tipoGuardado: String = "",
    val fechaGuardada: String = ""
)
