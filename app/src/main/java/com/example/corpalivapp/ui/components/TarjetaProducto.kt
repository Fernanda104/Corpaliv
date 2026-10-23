package com.example.corpalivapp.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.corpalivapp.data.model.Producto
import java.text.NumberFormat
import java.util.Locale

// Tarjeta de un producto: al tocarla se expande con animación y muestra la descripción.
@Composable
fun TarjetaProducto(
    producto: Producto,
    modifier: Modifier = Modifier
) {
    var expandida by rememberSaveable { mutableStateOf(false) }

    var icono = Icons.Filled.ExpandMore
    var accion = "Ver detalle"
    if (expandida) {
        icono = Icons.Filled.ExpandLess
        accion = "Ocultar detalle"
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClickLabel = accion, role = Role.Button) {
                expandida = !expandida
            }
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = formatearPrecio(producto.precio),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Icon(imageVector = icono, contentDescription = null)
            }

            if (!producto.disponible) {
                Text(
                    text = "Sin stock por ahora",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )
            }

            if (expandida) {
                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// Precio 0 = servicio con precio a convenir. Si no, en pesos chilenos ($6.990).
private fun formatearPrecio(precio: Double): String {
    if (precio <= 0.0) {
        return "Precio a convenir"
    }
    val formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CL"))
    return formato.format(precio)
}