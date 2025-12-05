package su.anfiox.core.uikit.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.annotations.VisibleForTesting
import su.anifox.domain.model.common.device.ThemeType

/**
 * Dark default theme color scheme
 */
@VisibleForTesting
internal val DarkColorScheme = darkColorScheme(
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
internal val LightColorScheme = lightColorScheme(
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

@Composable
fun AnifoxTheme(
    themeType: ThemeType = ThemeType.SYSTEM,
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themeType) {
        ThemeType.DARK -> true
        ThemeType.LIGHT -> false
        ThemeType.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    PlatformSideEffect(darkTheme = darkTheme)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = anifoxTypography(),
        content = content,
    )
}