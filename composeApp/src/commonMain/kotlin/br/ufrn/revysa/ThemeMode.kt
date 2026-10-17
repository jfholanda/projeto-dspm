package br.ufrn.revysa

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

internal enum class ThemeMode(val label: String) { System("Sistema"), Light("Claro"), Dark("Escuro") }

internal val lightColors =
    lightColorScheme(
        primary = Color(0xFF176B58),
        onPrimary = Color.White,
        primaryContainer = Color(0xFFDDF3E9),
        onPrimaryContainer = Color(0xFF123E32),
        secondary = Color(0xFF176B58),
        onSecondary = Color.White,
        secondaryContainer = Color(0xFFDDF3E9),
        onSecondaryContainer = Color(0xFF123E32),
        background = Color(0xFFF6F7F2),
        onBackground = Color(0xFF172B2A),
        surface = Color(0xFFF6F7F2),
        onSurface = Color(0xFF172B2A),
        surfaceVariant = Color(0xFFE1E8E2),
        onSurfaceVariant = Color(0xFF42534D),
        error = Color(0xFFBA1A1A),
    )

internal val darkColors =
    darkColorScheme(
        primary = Color(0xFF8BD5BA),
        onPrimary = Color(0xFF00382A),
        primaryContainer = Color(0xFF17513F),
        onPrimaryContainer = Color(0xFFB7F1D7),
        secondary = Color(0xFF8BD5BA),
        onSecondary = Color(0xFF00382A),
        secondaryContainer = Color(0xFF17513F),
        onSecondaryContainer = Color(0xFFB7F1D7),
        background = Color(0xFF101815),
        onBackground = Color(0xFFE0E8E1),
        surface = Color(0xFF101815),
        onSurface = Color(0xFFE0E8E1),
        surfaceVariant = Color(0xFF3F4943),
        onSurfaceVariant = Color(0xFFBFC9C1),
        error = Color(0xFFFFB4AB),
    )

@Composable
internal fun RevysaTheme(
    mode: ThemeMode,
    content: @Composable () -> Unit,
) {
    val dark =
        when (mode) {
            ThemeMode.System -> isSystemInDarkTheme()
            ThemeMode.Light -> false
            ThemeMode.Dark -> true
        }
    PlatformTheme(dark)
    MaterialTheme(colorScheme = if (dark) darkColors else lightColors, content = content)
}

@Composable
internal expect fun PlatformTheme(dark: Boolean)
