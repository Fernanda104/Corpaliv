package com.example.corpalivapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpalivapp.data.model.Colaboracion

// Tarjeta con los datos de una solicitud guardada y un botón para eliminarla.
@Composable
fun TarjetaSolicitud(
    colaboracion: Colaboracion,
    alEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    var textoPersonas = "${colaboracion.cantidadPersonas} personas"
    if (colaboracion.cantidadPersonas == 1) {
        textoPersonas = "1 persona"
    }

    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(start = 16.dp, top = 12.dp, bottom = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = colaboracion.tipoColaboracion,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "${colaboracion.nombreContacto} · ${colaboracion.organizacion}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = colaboracion.correo,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Contacto: ${colaboracion.fechaContacto} · $textoPersonas",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            IconButton(onClick = alEliminar) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Eliminar solicitud de ${colaboracion.tipoColaboracion}"
                )
            }
        }
    }
}