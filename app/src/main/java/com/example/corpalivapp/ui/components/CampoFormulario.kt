package com.example.corpalivapp.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

// Campo de texto con ícono, mensaje de error animado e ícono de error.
// No valida nada: solo muestra el valor y el error que le entrega el ViewModel.
@Composable
fun CampoFormulario(
    valor: String,                      // lo que muestra el campo
    alCambiar: (String) -> Unit,        // función que se llama al escribir
    etiqueta: String,                   // es el texto del campo
    icono: ImageVector,                 // ícono que se muestra a la izquierda
    error: String?,                     // mensaje de error del ViewModel
    modifier: Modifier = Modifier,      // modificador para personalizar el campo
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    val hayError = error != null
    // componente reutilizable para todos los campos de texto
    OutlinedTextField(      // es el campo de texto
        value = valor,
        onValueChange = alCambiar,
        label = { Text(etiqueta) },
        leadingIcon = {
            Icon(imageVector = icono, contentDescription = null)
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
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = tipoTeclado,
            imeAction = ImeAction.Next
        ),
        modifier = modifier.fillMaxWidth()
    )
}