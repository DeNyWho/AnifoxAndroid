package club.anifox.android.commonui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    background = blue900,
    onBackground = grey50,
    surfaceVariant = blue900,
    onSurfaceVariant = grey400,
    scrim = grey900,
    onSurface = grey600,
)

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    background = grey50,
    onBackground = blue900,
    surfaceVariant = grey50,
    onSurfaceVariant = grey400,
    scrim = grey900,
    onSurface = grey600,
)

@Composable
fun AnifoxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if(!darkTheme) {
        LightColorScheme
    } else {
        DarkColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
//        typography = Typography,
        content = content,
    )
}