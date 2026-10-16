package com.example.corpalivapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = lightColorScheme()

@Composable
fun MiAppTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = AppColorScheme,
        content = content
    )
}