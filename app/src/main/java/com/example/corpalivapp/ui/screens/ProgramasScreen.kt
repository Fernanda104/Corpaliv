
package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpalivapp.data.local.CorpalivDatabase
import com.example.corpalivapp.ui.components.FondoCorpaliv
import com.example.corpalivapp.viewmodel.CorpalivViewModelFactory
import com.example.corpalivapp.viewmodel.ProgramaViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.corpalivapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgramasScreen(
    onVolver: () -> Unit,
    database: CorpalivDatabase
) {
    val factory = CorpalivViewModelFactory(database)
    val programaViewModel: ProgramaViewModel = viewModel(
        factory = factory
    )

    val programas by programaViewModel.programas
        .collectAsStateWithLifecycle()

    FondoCorpaliv {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = androidx.compose.ui.graphics.Color.Transparent,
            topBar = {
                TopAppBar(
                    title = {
                        Text("Nuestros programas")
                    },
                    navigationIcon = {
                        IconButton(onClick = onVolver) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver al inicio"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface
                            .copy(alpha = 0.92f),
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor =
                            MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Conoce las iniciativas de CORPALIV " +
                            "orientadas a la inclusión y el desarrollo.",
                    color = MaterialTheme.colorScheme.onBackground
                )

                if (programas.isEmpty()) {
                    Text(
                        text = "Todavía no hay programas registrados.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(
                            items = programas,
                            key = { programa -> programa.id }
                        ) { programa ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor =
                                        MaterialTheme.colorScheme.surface,
                                    contentColor =
                                        MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement =
                                        Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = programa.nombre,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Text(
                                        text = programa.descripcion,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Image(
                                        painter = painterResource(
                                            id = if (programa.nombre == "Programa de Inclusión Laboral") {
                                                R.drawable.programa_inclusion_laboral
                                            } else {
                                                R.drawable.taller_sociolaboral
                                            }
                                        ),
                                        contentDescription = programa.nombre,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(180.dp),
                                        contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }

                Button(onClick = onVolver) {
                    Text("Volver al inicio")
                }
            }
        }
    }
}
