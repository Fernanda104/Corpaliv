package com.example.corpalivapp.acciones

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

// Abre apps del teléfono para contactar a CORPALIV.
// Cada función devuelve false si el teléfono no tiene una app para hacerlo.
object AccionesContacto {

    // Datos de ejemplo: el caso prohíbe usar datos personales reales.
    private const val TELEFONO = "+56222000000"
    private const val CORREO = "contacto@corpaliv.cl"
    private const val DIRECCION = "Santiago, Chile"
    private const val ASUNTO_CORREO = "Quiero colaborar con CORPALIV"

    fun llamar(context: Context): Boolean {
        val intento = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$TELEFONO"))
        return abrir(context, intento)
    }

    fun escribirCorreo(context: Context): Boolean {
        val intento = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$CORREO"))
        intento.putExtra(Intent.EXTRA_SUBJECT, ASUNTO_CORREO)
        return abrir(context, intento)
    }

    fun abrirMapa(context: Context): Boolean {
        val busqueda = Uri.encode(DIRECCION)
        val intento = Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=$busqueda"))
        return abrir(context, intento)
    }

    // Si no hay app que responda, Android lanza ActivityNotFoundException.
    private fun abrir(context: Context, intento: Intent): Boolean {
        return try {
            context.startActivity(intento)
            true
        } catch (e: ActivityNotFoundException) {
            false
        }
    }
}