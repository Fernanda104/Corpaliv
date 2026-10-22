package com.example.corpalivapp.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.corpalivapp.data.local.CorpalivDatabase
import com.example.corpalivapp.ui.screens.CatalogoScreen
import com.example.corpalivapp.ui.screens.ContactoScreen
import com.example.corpalivapp.ui.screens.HomeScreen
import com.example.corpalivapp.ui.screens.ProgramasScreen

@Composable
fun AppNavigation(database: CorpalivDatabase) {
    var pantallaActual by rememberSaveable {
        mutableStateOf("inicio")
    }

    BackHandler(enabled = pantallaActual != "inicio") {
        pantallaActual = "inicio"
    }

    // Cada vez que cambia pantallaActual, la pantalla vieja sale y la nueva entra deslizándose.
    AnimatedContent(
        targetState = pantallaActual,
        transitionSpec = {
            val vuelveAlInicio = targetState == "inicio"
            crearTransicion(vuelveAlInicio)
        },
        label = "navegacion"
    ) { pantalla ->
        when (pantalla) {
            "programas" -> ProgramasScreen(
                onVolver = { pantallaActual = "inicio" },
                database = database
            )

            "catalogo" -> CatalogoScreen(
                onVolver = { pantallaActual = "inicio" }
            )

            "contacto" -> ContactoScreen(
                onVolver = { pantallaActual = "inicio" },
                database = database
            )

            else -> HomeScreen(
                onProgramasClick = { pantallaActual = "programas" },
                onCatalogoClick = { pantallaActual = "catalogo" },
                onContactoClick = { pantallaActual = "contacto" }
            )
        }
    }
}

// Al entrar a una sección, la nueva pantalla llega desde la derecha.
// Al volver al inicio, llega desde la izquierda.
private fun crearTransicion(vuelveAlInicio: Boolean): ContentTransform {
    if (vuelveAlInicio) {
        val entrada = slideInHorizontally { ancho -> -ancho } + fadeIn()
        val salida = slideOutHorizontally { ancho -> ancho } + fadeOut()
        return entrada togetherWith salida
    }
    val entrada = slideInHorizontally { ancho -> ancho } + fadeIn()
    val salida = slideOutHorizontally { ancho -> -ancho } + fadeOut()
    return entrada togetherWith salida
}