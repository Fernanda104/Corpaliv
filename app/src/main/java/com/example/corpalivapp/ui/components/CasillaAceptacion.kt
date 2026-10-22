package com.example.corpalivapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

// Casilla de aceptación con error animado, igual que los demás campos del formulario.
@Composable
fun CasillaAceptacion(
    marcada: Boolean,
    alCambiar: (Boolean) -> Unit,
    texto: String,
    error: String?,
    modifier: Modifier = Modifier
) {
    val hayError = error != null

    var colorCasilla = MaterialTheme.colorScheme.onSurfaceVariant
    if (hayError) {
        colorCasilla = MaterialTheme.colorScheme.error
    }

    Column(modifier = modifier.fillMaxWidth()) {
        // Toda la fila se puede tocar, no solo el cuadrito.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(
                    value = marcada,
                    onValueChange = alCambiar,
                    role = Role.Checkbox
                )
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = marcada,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(uncheckedColor = colorCasilla)
            )
            Text(
                text = texto,
                modifier = Modifier.padding(start = 12.dp)
            )
        }

        AnimatedVisibility(
            visible = hayError,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Row(
                modifier = Modifier.padding(start = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}