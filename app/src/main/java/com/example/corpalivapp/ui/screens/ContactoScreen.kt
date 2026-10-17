package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpalivapp.data.local.CorpalivDatabase
import com.example.corpalivapp.ui.components.CampoFormulario
import com.example.corpalivapp.validacion.ColaboracionValidador
import com.example.corpalivapp.viewmodel.ColaboracionViewModel
import com.example.corpalivapp.viewmodel.CorpalivViewModelFactory

@Composable
fun ContactoScreen(
    onVolver: () -> Unit,
    database: CorpalivDatabase
) {
    val factory = remember(database) {
        CorpalivViewModelFactory(database)
    }

    val colaboracionViewModel: ColaboracionViewModel =
        viewModel(factory = factory)

    val formulario by colaboracionViewModel.formulario
        .collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Colabora con nosotros",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Completa el formulario para expresar tu interés " +
                    "en colaborar con CORPALIV."
        )

        CampoFormulario(
            valor = formulario.nombre,
            alCambiar = colaboracionViewModel::cambiarNombre,
            etiqueta = "Nombre completo",
            icono = Icons.Filled.Person,
            error = formulario.errorNombre
        )

        CampoFormulario(
            valor = formulario.organizacion,
            alCambiar = colaboracionViewModel::cambiarOrganizacion,
            etiqueta = "Organización",
            icono = Icons.Filled.Business,
            error = formulario.errorOrganizacion
        )

        CampoFormulario(
            valor = formulario.correo,
            alCambiar = colaboracionViewModel::cambiarCorreo,
            etiqueta = "Correo electrónico",
            icono = Icons.Filled.Email,
            error = formulario.errorCorreo,
            tipoTeclado = KeyboardType.Email
        )

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Tipo de colaboración",
                    style = MaterialTheme.typography.titleMedium
                )

                ColaboracionValidador.TIPOS_COLABORACION.forEach { tipo ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = formulario.tipo == tipo,
                            onClick = {
                                colaboracionViewModel.cambiarTipo(tipo)
                            }
                        )
                        Text(
                            text = tipo,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                if (formulario.errorTipo != null) {
                    Text(
                        text = formulario.errorTipo.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }

        CampoFormulario(
            valor = formulario.cantidadPersonas,
            alCambiar = colaboracionViewModel::cambiarCantidad,
            etiqueta = "Cantidad de personas",
            icono = Icons.Filled.Groups,
            error = formulario.errorCantidad,
            tipoTeclado = KeyboardType.Number
        )

        CampoFormulario(
            valor = formulario.fecha,
            alCambiar = colaboracionViewModel::cambiarFecha,
            etiqueta = "Fecha de contacto (dd/MM/aaaa)",
            icono = Icons.Filled.CalendarMonth,
            error = formulario.errorFecha
        )

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = formulario.aceptaContacto,
                    onCheckedChange =
                        colaboracionViewModel::cambiarAceptaContacto
                )

                Text(
                    text = "Acepto que CORPALIV me contacte " +
                            "en relación con mi solicitud."
                )
            }

            if (formulario.errorAceptaContacto != null) {
                Text(
                    text = formulario.errorAceptaContacto.orEmpty(),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }
        }

        Button(
            onClick = {
                colaboracionViewModel.enviarFormulario()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            androidx.compose.material3.Text("Enviar solicitud")
        }

        if (formulario.guardadoConExito) {
            Text(
                text = "¡Gracias! Tu solicitud se guardó correctamente.",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = colaboracionViewModel::ocultarMensajeExito,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar")
            }
        }

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al inicio")
        }
    }
}
