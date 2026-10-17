package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.corpalivapp.data.local.CorpalivDatabase
import com.example.corpalivapp.viewmodel.CorpalivViewModelFactory
import com.example.corpalivapp.viewmodel.ProgramaViewModel

@Composable
fun ProgramasScreen(
    onVolver: () -> Unit,
    database: CorpalivDatabase
) {
    val factory = CorpalivViewModelFactory(database)
    val programaViewModel: ProgramaViewModel =
        viewModel(factory = factory)

    val programas by programaViewModel.programas
        .collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Nuestros programas",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Conoce las iniciativas de CORPALIV " +
                    "orientadas a la inclusión y el desarrollo."
        )

        if (programas.isEmpty()) {
            Text(
                text = "Todavía no hay programas registrados.",
                style = MaterialTheme.typography.bodyLarge
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
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement =
                                Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = programa.nombre,
                                style =
                                    MaterialTheme.typography.titleMedium
                            )

                            Text(text = programa.descripcion)
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