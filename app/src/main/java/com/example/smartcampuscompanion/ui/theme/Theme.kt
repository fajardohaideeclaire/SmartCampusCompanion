package com.example.smartcampuscompanion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = GreenPrimary,
    secondary = GreenSecondary,
    background = WhiteBackground,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black
)

@Composable
fun SmartCampusTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
