package club.anifox.android.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    background = blue10,
    onBackground = grey90,
    surfaceVariant = blue10,
    onSurfaceVariant = grey60,
)

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    background = grey90,
    onBackground = blue10,
    surfaceVariant = grey90,
    onSurfaceVariant = grey60,
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