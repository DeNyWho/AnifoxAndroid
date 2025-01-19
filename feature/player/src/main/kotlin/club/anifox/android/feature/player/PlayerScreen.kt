package club.anifox.android.feature.player

import android.content.pm.ActivityInfo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.util.LockScreenOrientation
import club.anifox.android.core.uikit.util.findActivity
import club.anifox.android.feature.player.composable.component.kodik.WebPlayerKodik

@Composable
internal fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    url: String,
    kodik: Boolean = false,
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val window = context.findActivity()?.window?: return@DisposableEffect onDispose {}
        val windowInsets = WindowCompat.getInsetsController(window, window.decorView)
        windowInsets.apply {
            hide(WindowInsetsCompat.Type.systemBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        onDispose {}
    }

    PlayerUI(
        url = url,
        kodik = kodik,
    )
}

@Composable
private fun PlayerUI(
    url: String,
    kodik: Boolean,
) {

    // TODO Remove the line on the left
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.Black
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if(kodik) {
                WebPlayerKodik(url)
            }
        }
    }
}