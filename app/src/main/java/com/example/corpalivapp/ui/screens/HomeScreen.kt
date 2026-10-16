package com.example.corpalivapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val VerdeCorpaliv = Color(0xFF28594D)
private val FondoCrema = Color(0xFFF5F2EA)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onProgramasClick: () -> Unit = {},
    onCatalogoClick: () -> Unit = {},
    onContactoClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "CORPALIV | Alba Lab",
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        containerColor = FondoCrema
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = VerdeCorpaliv,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(24.dp)
            ) {
                Text(
                    text = "INCLUSIÓN QUE TRANSFORMA VIDAS",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Conectamos personas, empresas y " +
                            "oportunidades para construir una sociedad " +
                            "más inclusiva.",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            Text(
                text = "Descubre cómo participar",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = VerdeCorpaliv
            )

            SeccionCard(
                titulo = "Nuestros programas",
                descripcion = "Conoce nuestras iniciativas de inclusión y desarrollo.",
                textoBoton = "Explorar programas",
                onClick = onProgramasClick
            )

            SeccionCard(
                titulo = "Productos y servicios",
                descripcion = "Descubre propuestas con impacto social.",
                textoBoton = "Ver catálogo",
                onClick = onCatalogoClick
            )

            SeccionCard(
                titulo = "Colabora con nosotros",
                descripcion = "Conversemos sobre donaciones, colaboraciones y alianzas.",
                textoBoton = "Quiero colaborar",
                onClick = onContactoClick
            )
        }
    }
}

@Composable
private fun SeccionCard(
    titulo: String,
    descripcion: String,
    textoBoton: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = VerdeCorpaliv
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = descripcion,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeCorpaliv
                )
            ) {
                Text(text = textoBoton)
            }
        }
    }
}