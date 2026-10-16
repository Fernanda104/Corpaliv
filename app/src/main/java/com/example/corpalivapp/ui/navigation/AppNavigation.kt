package com.example.corpalivapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.corpalivapp.ui.screens.CatalogoScreen
import com.example.corpalivapp.ui.screens.ContactoScreen
import com.example.corpalivapp.ui.screens.HomeScreen
import com.example.corpalivapp.ui.screens.ProgramasScreen

@Composable
fun AppNavigation() {
    var pantallaActual by rememberSaveable {
        mutableStateOf("inicio")
    }

    BackHandler(enabled = pantallaActual != "inicio") {
        pantallaActual = "inicio"
    }

    when (pantallaActual) {
        "programas" -> {
            ProgramasScreen(
                onVolver = { pantallaActual = "inicio" }
            )
        }

        "catalogo" -> {
            CatalogoScreen(
                onVolver = { pantallaActual = "inicio" }
            )
        }

        "contacto" -> {
            ContactoScreen(
                onVolver = { pantallaActual = "inicio" }
            )
        }

        else -> {
            HomeScreen(
                onProgramasClick = {
                    pantallaActual = "programas"
                },
                onCatalogoClick = {
                    pantallaActual = "catalogo"
                },
                onContactoClick = {
                    pantallaActual = "contacto"
                }
            )
        }
    }
}