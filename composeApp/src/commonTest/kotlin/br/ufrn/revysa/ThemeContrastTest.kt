package br.ufrn.revysa

import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import kotlin.test.Test
import kotlin.test.assertTrue

class ThemeContrastTest {
    @Test
    fun lightThemeTextMeetsWcagAaContrast() = checkPalette(lightColors)

    @Test
    fun darkThemeTextMeetsWcagAaContrast() = checkPalette(darkColors)

    private fun checkPalette(colors: ColorScheme) {
        val pairs =
            listOf(
                colors.onSurface to colors.surface,
                colors.onSurfaceVariant to colors.surfaceVariant,
                colors.onPrimary to colors.primary,
                colors.onPrimaryContainer to colors.primaryContainer,
                colors.primary to colors.surface,
                colors.error to colors.surface,
            )
        pairs.forEach { (foreground, background) ->
            assertTrue(contrast(foreground, background) >= MINIMUM_CONTRAST, "Contraste insuficiente: $foreground / $background")
        }
    }

    private fun contrast(
        first: Color,
        second: Color,
    ): Float {
        val values = listOf(first.luminance(), second.luminance())
        return (values.max() + LUMINANCE_OFFSET) / (values.min() + LUMINANCE_OFFSET)
    }

    private companion object {
        const val MINIMUM_CONTRAST = 4.5f
        const val LUMINANCE_OFFSET = 0.05f
    }
}
