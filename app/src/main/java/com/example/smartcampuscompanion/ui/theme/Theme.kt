package com.example.smartcampuscompanion.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Light color scheme ────────────────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary            = MediumGreen,
    onPrimary          = Color.White,
    primaryContainer   = PaleGreen,
    onPrimaryContainer = DarkGreen,
    secondary          = DarkGreen,
    onSecondary        = Color.White,
    background         = Color(0xFFF6F8F7),
    onBackground       = Color(0xFF1C1C1E),
    surface            = Color.White,
    onSurface          = Color(0xFF1C1C1E),
    surfaceVariant     = Color(0xFFF0F4F2),
    onSurfaceVariant   = Color(0xFF6B6B6B),
    error              = Color(0xFFE53935),
    onError            = Color.White,
)

// ── Dark color scheme ─────────────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary            = MediumGreen,
    onPrimary          = Color.White,
    primaryContainer   = Color(0xFF1A3D2B),
    onPrimaryContainer = Color(0xFF9FDFB8),
    secondary          = Color(0xFF9FDFB8),
    onSecondary        = Color(0xFF003822),
    background         = Color(0xFF121212),
    onBackground       = Color(0xFFE6E1E5),
    surface            = Color(0xFF1E1E1E),
    onSurface          = Color(0xFFE6E1E5),
    surfaceVariant     = Color(0xFF2A2A2A),
    onSurfaceVariant   = Color(0xFFCAC4D0),
    error              = Color(0xFFCF6679),
    onError            = Color(0xFF370B1E),
)

// ── Global dark mode state ─────────────────────────────────────────────────────
// This allows toggling dark mode from SettingsScreen
val LocalDarkMode = compositionLocalOf { false }

@Composable
fun SmartCampusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}