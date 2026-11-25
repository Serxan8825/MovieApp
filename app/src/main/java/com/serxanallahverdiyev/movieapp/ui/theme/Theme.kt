package com.serxanallahverdiyev.movieapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = AccentPurple,
    onPrimary = PrimaryWhite,
    secondary = SecondaryGray,
    onSecondary = PrimaryWhite,
    background = BackgroundBlack,
    onBackground = PrimaryWhite,
    surface = SurfaceDark,
    onSurface = PrimaryWhite,
    error = ErrorRed,
    onError = PrimaryWhite
)

@Composable
fun MovieAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
