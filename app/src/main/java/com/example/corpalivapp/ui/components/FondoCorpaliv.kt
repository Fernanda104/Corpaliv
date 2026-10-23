package com.example.corpalivapp.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.corpalivapp.R

@Composable
fun FondoCorpaliv(
    modifier: Modifier = Modifier,
    contenido: @Composable BoxScope.() -> Unit
) {
    val modoOscuro = isSystemInDarkTheme()

    val imagen = if (modoOscuro) {
        R.drawable.fondo_corpaliv_oscuro
    } else {
        R.drawable.fondo_corpaliv_claro
    }

    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = imagen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        contenido()
    }
}
