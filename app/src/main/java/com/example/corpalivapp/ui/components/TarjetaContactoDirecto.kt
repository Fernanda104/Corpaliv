package com.example.corpalivapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

// Tarjeta con accesos directos para contactar a CORPALIV sin llenar el formulario.
@Composable
fun TarjetaContactoDirecto(
    alLlamar: () -> Unit,
    alEscribirCorreo: () -> Unit,
    alAbrirMapa: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "¿Prefieres hablar directo?",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Comunícate con el equipo de vinculación de CORPALIV.",
                style = MaterialTheme.typography.bodyMedium
            )

            BotonContacto(texto = "Llamar", icono = Icons.Filled.Call, alTocar = alLlamar)
            BotonContacto(texto = "Escribir un correo", icono = Icons.Filled.Email, alTocar = alEscribirCorreo)
            BotonContacto(texto = "Cómo llegar a la sede", icono = Icons.Filled.Place, alTocar = alAbrirMapa)
        }
    }
}

@Composable
private fun BotonContacto(
    texto: String,
    icono: ImageVector,
    alTocar: () -> Unit
) {
    OutlinedButton(
        onClick = alTocar,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(imageVector = icono, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = texto)
    }
}