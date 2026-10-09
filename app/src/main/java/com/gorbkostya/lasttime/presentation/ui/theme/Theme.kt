package com.gorbkostya.lasttime.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    background = DarkBg,
    surface = DarkCard,
    surfaceVariant = DarkMuted,
    onBackground = DarkText,
    onSurface = DarkText,
    onSurfaceVariant = DarkText2,
    outlineVariant = DarkBorder,
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary
)

private val LightColorScheme = lightColorScheme(
    background = LightBg,
    surface = LightCard,
    surfaceVariant = LightMuted,
    onBackground = LightText,
    onSurface = LightText,
    onSurfaceVariant = LightText2,
    outlineVariant = LightBorder,
    primary = LightPrimary,
    onPrimary = LightOnPrimary
)

@Composable
fun LastTimeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }
    val extra = if (darkTheme) {
        DarkExtra
    } else {
        LightExtra
    }

    CompositionLocalProvider(LocalExtraColors provides extra) {
        MaterialTheme(
            colorScheme = colors,
            typography = Typography,
            content = content
        )
    }
}


data class ExtraColors(
    val ok: Color,
    val soon: Color,
    val late: Color,
    val okTint: Color,
    val soonTint: Color,
    val lateTint: Color
)


private val LightExtra = ExtraColors(
    ok = LightOk,
    soon = LightSoon,
    late = LightLate,
    okTint = LightOkTint,
    soonTint = LightSoonTint,
    lateTint = LightLateTint
)


private val DarkExtra = ExtraColors(
    ok = DarkOk,
    soon = DarkSoon,
    late = DarkLate,
    okTint = DarkOkTint,
    soonTint = DarkSoonTint,
    lateTint = DarkLateTint
)


val LocalExtraColors = staticCompositionLocalOf { LightExtra }


val MaterialTheme.extra: ExtraColors
    @Composable get() = LocalExtraColors.current