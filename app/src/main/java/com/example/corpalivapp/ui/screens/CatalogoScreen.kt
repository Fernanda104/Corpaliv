
package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.corpalivapp.ui.components.FondoCorpaliv

@Composable
fun CatalogoScreen(
    onVolver: () -> Unit
) {
    FondoCorpaliv {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Productos y servicios",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Aquí presentaremos los productos y servicios " +
                        "con impacto social de Alba Lab.",
                color = MaterialTheme.colorScheme.onBackground
            )

            Button(
                onClick = onVolver
            ) {
                Text("Volver al inicio")
            }
        }
    }
}
