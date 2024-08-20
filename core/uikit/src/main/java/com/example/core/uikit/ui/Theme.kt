package com.example.core.uikit.ui
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = Pink,
    secondary = White,
    background = Black,
    surface = Teal700,
    onPrimary = White,
    onSecondary = Black,
    onBackground = White,
    onSurface = White,
    error = TextError,
    onError = TextErrorContrast
)

private val LightColorScheme = lightColorScheme(
    primary = Pink,
    secondary = White,
    background = Crayola,
    surface = Gray,
    onPrimary = White,
    onSecondary = Black,
    onBackground = Black,
    onSurface = Black,
    error = TextError,
    onError = TextErrorContrast
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {
    val colors = LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        shapes = Shapes,
        typography = Typography,
        content = content
    )
}
