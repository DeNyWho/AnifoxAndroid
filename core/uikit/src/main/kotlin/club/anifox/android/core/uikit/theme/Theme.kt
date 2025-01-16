package club.anifox.android.core.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import club.anifox.android.core.uikit.util.LocalScreenInfo
import org.jetbrains.annotations.VisibleForTesting

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    background = blue900,
    onBackground = grey50,
    surfaceVariant = grey800,
    onSurfaceVariant = grey400,
    scrim = grey900,
    onSurface = grey600,
    surface = grey700,
    primary = orange800,
    onPrimary = grey50,
    surfaceContainer = blue900,
    primaryContainer = orange800,
    onPrimaryContainer = grey50,
    error = grey50,
)

/**
 * Light default theme color scheme
 */
@VisibleForTesting
val LightColorScheme = lightColorScheme(
    background = grey50,
    onBackground = blue900,
    surfaceVariant = grey200,
    onSurfaceVariant = grey400,
    scrim = grey900,
    onSurface = grey600,
    surface = grey700,
    primary = orange800,
    onPrimary = grey50,
    surfaceContainer = grey50,
    primaryContainer = orange800,
    onPrimaryContainer = grey50,
    error = Color.Red,
)

/**
 * Anifox theme.
 *
 * @param darkTheme Whether the theme should use a dark color scheme (follows system by default).
 */
@Composable
fun AnifoxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if(!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    val screenInfo = LocalScreenInfo.current
    val fontSizePrefs = screenInfo.fontSizePrefs

    MaterialTheme(
        colorScheme = colorScheme,
        typography = anifoxTypography(fontSizePrefs = fontSizePrefs),
        content = content,
    )
}