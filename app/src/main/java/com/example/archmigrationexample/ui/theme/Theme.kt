package com.example.archmigrationexample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = BrandRed,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDAD4),
    onPrimaryContainer = BrandRedDark,
    secondary = BrandAccent,
    onSecondary = Color(0xFF3E2723),
    background = ScreenBackground,
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFF2F2F2),
    onSurfaceVariant = Color(0xFF49454F),
    error = BrandRedDark
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFFF8A80),
    onPrimary = Color(0xFF690005),
    primaryContainer = BrandRedDark,
    onPrimaryContainer = Color(0xFFFFDAD4),
    secondary = BrandAccent,
    onSecondary = Color(0xFF3E2723),
    background = Color(0xFF121212),
    onBackground = Color.White,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF2C2C2C),
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = Color(0xFFFF8A80)
)

@Composable
fun ArchMigrationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
