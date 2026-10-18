package com.example.corpalivapp.validacion

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// Reglas del formulario de colaboración.
// Cada función devuelve el mensaje de error, o null si el valor es válido.


object ColaboracionValidador {  // object crea una sola instancia que se usa directamente

    val TIPOS_COLABORACION = listOf(
        "Donación",
        "Alianza",
        "Voluntariado",
        "Compra de productos",
        "Capacitación"
    )

    private const val CANTIDAD_MINIMA = 1
    private const val CANTIDAD_MAXIMA = 500

    private val formatoCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val formatoFecha = Regex("^\\d{2}/\\d{2}/\\d{4}$")

// Una función por cada campo del formulario, cada una recibe el valor tal como lo escribió el usuario.
    fun validarNombre(nombre: String): String? {
        val limpio = nombre.trim()
        if (limpio.isEmpty()) {
            return "El nombre es obligatorio"
        }
        if (limpio.length < 3) {
            return "El nombre debe tener al menos 3 letras"
        }
        val soloLetras = limpio.all { it.isLetter() || it == ' ' }
        if (!soloLetras) {
            return "El nombre solo puede tener letras"
        }
        return null
    }

    fun validarOrganizacion(organizacion: String): String? {
        val limpia = organizacion.trim()
        if (limpia.isEmpty()) {
            return "La organización es obligatoria"
        }
        if (limpia.length < 2) {
            return "La organización debe tener al menos 2 caracteres"
        }
        return null
    }

    fun validarCorreo(correo: String): String? {
        val limpio = correo.trim()
        if (limpio.isEmpty()) {
            return "El correo es obligatorio"
        }
        if (!formatoCorreo.matches(limpio)) {
            return "Ingresa un correo válido, por ejemplo nombre@correo.cl"
        }
        return null
    }

    fun validarTipo(tipo: String): String? {
        if (tipo.isEmpty()) {
            return "Elige un tipo de colaboración"
        }
        if (tipo !in TIPOS_COLABORACION) {
            return "Tipo de colaboración no válido"
        }
        return null
    }

    fun validarCantidad(cantidad: String): String? {
        val limpia = cantidad.trim()
        if (limpia.isEmpty()) {
            return "La cantidad de personas es obligatoria"
        }
        val numero = limpia.toIntOrNull()
        if (numero == null) {
            return "Ingresa un número entero"
        }
        if (numero < CANTIDAD_MINIMA || numero > CANTIDAD_MAXIMA) {
            return "Debe ser entre $CANTIDAD_MINIMA y $CANTIDAD_MAXIMA personas"
        }
        return null
    }

    fun validarFecha(fecha: String): String? {
        val limpia = fecha.trim()
        if (limpia.isEmpty()) {
            return "La fecha es obligatoria"
        }
        if (!formatoFecha.matches(limpia)) {
            return "Usa el formato dd/MM/aaaa"
        }
        val fechaIngresada = convertirFecha(limpia)
        if (fechaIngresada == null) {
            return "Esa fecha no existe"
        }
        if (fechaIngresada.before(obtenerHoy())) {
            return "La fecha no puede ser anterior a hoy"
        }
        return null
    }

    fun validarAceptaContacto(acepta: Boolean): String? {
        if (!acepta) {
            return "Debes aceptar ser contactado para enviar"
        }
        return null
    }

    // Convierte "dd/MM/aaaa" a Date. Devuelve null si la fecha no existe (ej: 31/02).
    private fun convertirFecha(texto: String): Date? {
        val formateador = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
        formateador.isLenient = false
        return try {
            formateador.parse(texto)
        } catch (e: ParseException) {
            null
        }
    }

    // Hoy a las 00:00, para que la fecha de hoy cuente como válida.
    private fun obtenerHoy(): Date {
        val hoy = Calendar.getInstance()
        hoy.set(Calendar.HOUR_OF_DAY, 0)
        hoy.set(Calendar.MINUTE, 0)
        hoy.set(Calendar.SECOND, 0)
        hoy.set(Calendar.MILLISECOND, 0)
        return hoy.time
    }
}