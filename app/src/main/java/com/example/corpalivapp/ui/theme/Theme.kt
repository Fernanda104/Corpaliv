
package com.example.corpalivapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val EsquemaClaro = lightColorScheme(
    primary = Color(0xFF00695C),
    onPrimary = Color.White,
    secondary = Color(0xFF4F7972),
    background = Color(0xFFF5F2EA),
    onBackground = Color(0xFF191C1B),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF191C1B)
)

private val EsquemaOscuro = darkColorScheme(
    primary = Color(0xFF80CBC4),
    onPrimary = Color(0xFF003731),
    secondary = Color(0xFFA4CEC6),
    background = Color(0xFF121615),
    onBackground = Color(0xFFE0E3E1),
    surface = Color(0xFF1C2220),
    onSurface = Color(0xFFE0E3E1)
)

@Composable
fun MiAppTheme(
    content: @Composable () -> Unit
) {
    val modoOscuro = isSystemInDarkTheme()

    val esquemaColores = if (modoOscuro) {
        EsquemaOscuro
    } else {
        EsquemaClaro
    }

    MaterialTheme(
        colorScheme = esquemaColores,
        content = content
    )
}
