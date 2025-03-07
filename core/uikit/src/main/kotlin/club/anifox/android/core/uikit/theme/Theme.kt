package club.anifox.android.core.uikit.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.common.device.ThemeType
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
 * @param themeType Determines the color scheme of the theme (follows system settings by default).
 *                  Available options: light, dark, or system.
 */
@Composable
fun AnifoxTheme(
    themeType: ThemeType = ThemeType.SYSTEM,
    content: @Composable () -> Unit,
) {
    val colorScheme = when (themeType) {
        ThemeType.DARK -> DarkColorScheme
        ThemeType.LIGHT -> LightColorScheme
        ThemeType.SYSTEM -> {
            if (!isSystemInDarkTheme()) {
                LightColorScheme
            } else {
                DarkColorScheme
            }
        }
    }
    val darkTheme = when (themeType) {
        ThemeType.DARK -> true
        ThemeType.LIGHT -> false
        ThemeType.SYSTEM -> {
            isSystemInDarkTheme()
        }
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars =
                !darkTheme // negate darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars =
                !darkTheme
        }
    }

    val screenInfo = LocalScreenInfo.current
    val fontSizePrefs = screenInfo.fontSizePrefs

    MaterialTheme(
        colorScheme = colorScheme,
        typography = anifoxTypography(fontSizePrefs = fontSizePrefs),
        content = content,
    )
}