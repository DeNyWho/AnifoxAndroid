package club.anifox.android.feature.player

import android.content.pm.ActivityInfo
import android.os.Build
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.util.LockScreenOrientation
import club.anifox.android.core.uikit.util.findActivity
import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.feature.player.component.kodik.WebPlayerKodikComponent

@Composable
internal fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    url: String,
    kodik: Boolean = false,
) {
    val selectedPlayerOrientationState by viewModel.selectedPlayerOrientation.collectAsState()

    if(selectedPlayerOrientationState == PlayerOrientation.HORIZONTAL) {
        LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
    }

    val context = LocalContext.current

    DisposableEffect(Unit) {
        val window = context.findActivity()?.window ?: return@DisposableEffect onDispose {}

        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)

        val originalSystemBarsBehavior = windowInsetsController.systemBarsBehavior

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && selectedPlayerOrientationState == PlayerOrientation.HORIZONTAL) {
            val originalCutoutMode = window.attributes.layoutInDisplayCutoutMode
            val layoutParams = window.attributes
            layoutParams.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_NEVER
            window.attributes = layoutParams

            onDispose {
                val restoreParams = window.attributes
                restoreParams.layoutInDisplayCutoutMode = originalCutoutMode
                window.attributes = restoreParams
            }

            windowInsetsController.apply {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }

        onDispose {
            val restoredWindowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
            restoredWindowInsetsController.systemBarsBehavior = originalSystemBarsBehavior

            restoredWindowInsetsController.show(WindowInsetsCompat.Type.systemBars())
        }
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
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if(kodik) {
                WebPlayerKodikComponent(url)
            }
        }
    }
}