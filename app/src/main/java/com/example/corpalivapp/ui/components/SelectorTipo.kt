package com.example.corpalivapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

// Menú desplegable para elegir el tipo de colaboración.
// Solo muestra las opciones y el error; el ViewModel decide si es válido.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectorTipo(
    seleccionado: String,
    opciones: List<String>,
    alElegir: (String) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    var abierto by remember { mutableStateOf(false) }
    // abierto guarda si el menú está desplegado. remember lo mantiene entre redibujos.
    val hayError = error != null

    ExposedDropdownMenuBox(
        expanded = abierto,
        onExpandedChange = { abierto = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = seleccionado,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de colaboración") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.VolunteerActivism, contentDescription = null)
            },
            trailingIcon = {    // es la flecha ▼/▲
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = abierto)
            },
            isError = hayError,
            supportingText = {
                AnimatedVisibility(
                    visible = hayError,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Text(text = error ?: "")
                }
            },
            modifier = Modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = abierto,
            onDismissRequest = { abierto = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        alElegir(opcion)
                        abierto = false
                    }
                )
            }
        }
    }
}