package club.anifox.android.core.uikit.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun calculateGridCount(columnWidth: Dp): Int {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    return (screenWidth / columnWidth).toInt().coerceIn(2, 3)
}