package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpalivapp.data.local.CorpalivDatabase
import com.example.corpalivapp.data.model.Colaboracion
import com.example.corpalivapp.ui.components.FondoCorpaliv
import com.example.corpalivapp.ui.components.TarjetaSolicitud
import com.example.corpalivapp.viewmodel.ColaboracionViewModel
import com.example.corpalivapp.viewmodel.CorpalivViewModelFactory
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolicitudesScreen(
    onVolver: () -> Unit,
    database: CorpalivDatabase
) {
    val factory = remember(database) {
        CorpalivViewModelFactory(database)
    }

    val colaboracionViewModel: ColaboracionViewModel =
        viewModel(factory = factory)

    val colaboraciones by colaboracionViewModel.colaboraciones
        .collectAsStateWithLifecycle()

    val estadoSnackbar = remember { SnackbarHostState() }
    val alcance = rememberCoroutineScope()

    // Solicitud que la persona quiere borrar; null = no hay diálogo abierto.
    var porEliminar by remember { mutableStateOf<Colaboracion?>(null) }

    Scaffold(
        containerColor = Color.Transparent,
        topBar = {
            TopAppBar(
                title = { Text("Mis solicitudes") },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver al inicio"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = estadoSnackbar) }
    ) { paddingValues ->

        // Fondo de CORPALIV que cambia según el tema claro u oscuro.
        FondoCorpaliv {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    Text(
                        text = "Solicitudes de colaboración enviadas desde este teléfono.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                if (colaboraciones.isEmpty()) {
                    item {
                        Text(
                            text = "Todavía no has enviado solicitudes. " +
                                    "Puedes hacerlo en \"Colabora con nosotros\".",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                items(
                    items = colaboraciones,
                    key = { colaboracion -> colaboracion.id }
                ) { colaboracion ->
                    TarjetaSolicitud(
                        colaboracion = colaboracion,
                        alEliminar = { porEliminar = colaboracion },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }

    // Se copia a un val porque Kotlin no deja revisar null directo en un "by remember".
    val seleccionada = porEliminar
    if (seleccionada != null) {
        DialogoEliminar(
            tipo = seleccionada.tipoColaboracion,
            alConfirmar = {
                colaboracionViewModel.eliminar(seleccionada)
                porEliminar = null
                alcance.launch {
                    estadoSnackbar.showSnackbar("Solicitud eliminada.")
                }
            },
            alCancelar = { porEliminar = null }
        )
    }
}

@Composable
private fun DialogoEliminar(
    tipo: String,
    alConfirmar: () -> Unit,
    alCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = alCancelar,
        title = { Text("¿Eliminar esta solicitud?") },
        text = { Text("Se borrará la solicitud de $tipo de este teléfono. No se puede deshacer.") },
        confirmButton = {
            TextButton(onClick = alConfirmar) {
                Text(
                    text = "Eliminar",
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        dismissButton = {
            TextButton(onClick = alCancelar) {
                Text("Cancelar")
            }
        }
    )
}