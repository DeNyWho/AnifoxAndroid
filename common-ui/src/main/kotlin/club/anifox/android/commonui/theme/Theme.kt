package club.anifox.android.commonui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import org.jetbrains.annotations.VisibleForTesting

@VisibleForTesting
val DarkColorScheme = darkColorScheme(
    background = club.anifox.android.commonui.theme.blue10,
    onBackground = club.anifox.android.commonui.theme.grey90,
    surfaceVariant = club.anifox.android.commonui.theme.blue10,
    onSurfaceVariant = club.anifox.android.commonui.theme.grey60,
)

@VisibleForTesting
val LightColorScheme = lightColorScheme(
    background = club.anifox.android.commonui.theme.grey90,
    onBackground = club.anifox.android.commonui.theme.blue10,
    surfaceVariant = club.anifox.android.commonui.theme.grey90,
    onSurfaceVariant = club.anifox.android.commonui.theme.grey60,
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