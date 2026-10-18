package com.example.corpalivapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

// Campo de fecha de solo lectura: al tocarlo se abre el calendario.
@Composable
fun CampoFecha(
    fecha: String,
    alElegir: (Long) -> Unit,
    error: String?,
    modifier: Modifier = Modifier
) {
    var mostrarCalendario by remember { mutableStateOf(false) }
    val hayError = error != null

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = fecha,
            onValueChange = {},
            readOnly = true,
            label = { Text("Fecha de contacto") },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = null)
            },
            trailingIcon = {
                if (hayError) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = "Error",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
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
            modifier = Modifier.fillMaxWidth()
        )

        // Capa transparente encima del campo: recibe el toque y abre el calendario.
        Box(
            modifier = Modifier
                .matchParentSize()
                .clickable(onClickLabel = "Elegir fecha") {
                    mostrarCalendario = true
                }
        )
    }

    if (mostrarCalendario) {
        DialogoCalendario(
            alConfirmar = { milisegundos ->
                alElegir(milisegundos)
                mostrarCalendario = false
            },
            alCerrar = { mostrarCalendario = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogoCalendario(
    alConfirmar: (Long) -> Unit,
    alCerrar: () -> Unit
) {
    val estadoCalendario = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = alCerrar,
        confirmButton = {
            TextButton(
                onClick = {
                    val elegida = estadoCalendario.selectedDateMillis
                    if (elegida != null) {
                        alConfirmar(elegida)
                    } else {
                        alCerrar()
                    }
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = alCerrar) {
                Text("Cancelar")
            }
        }
    ) {
        DatePicker(state = estadoCalendario)
    }
}