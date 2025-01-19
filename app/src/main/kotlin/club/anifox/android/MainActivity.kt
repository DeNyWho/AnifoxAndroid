package club.anifox.android

import android.os.Build
import android.os.Bundle
import android.view.WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import club.anifox.android.core.common.util.network.NetworkMonitor
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.common.device.FontSizePrefs
import club.anifox.android.domain.model.common.device.ScreenInfo
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.ui.AnifoxApp
import club.anifox.android.ui.rememberAnifoxAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var keepSplashScreen by mutableStateOf(true)

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreen
        }

        // gives minimal changes (the content does not stretch across the entire screen, it has been checked with all the checkboxes.)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode = LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        setContent {
            val isFirstLaunch by mainViewModel.isFirstLaunch.collectAsState()
            val fontSizePrefs by mainViewModel.fontSizePrefs.collectAsState()
            val screenInfo = remember { getScreenInfo() }

            LaunchedEffect(isFirstLaunch) {
                if (isFirstLaunch != null) {
                    if (isFirstLaunch as Boolean) {
                        mainViewModel.onFirstLaunch(screenInfo.screenType)
                    }
                    keepSplashScreen = false
                }
            }

            if (isFirstLaunch != null) {
                val updatedScreenInfo = screenInfo.copy(fontSizePrefs = fontSizePrefs)
                val appState = rememberAnifoxAppState(
                    networkMonitor = networkMonitor,
                    isFirstLaunch = isFirstLaunch!!,
                )
                CompositionLocalProvider(LocalScreenInfo provides updatedScreenInfo) {
                    AnifoxTheme {
                        AnifoxApp(appState)
                    }
                }
            }
        }
    }

    private fun getScreenInfo(): ScreenInfo {
        val displayMetrics = resources.displayMetrics
        val density = displayMetrics.density

        val widthDp = displayMetrics.widthPixels / density
        val heightDp = displayMetrics.heightPixels / density

        val (portraitWidthDp, portraitHeightDp) = if (widthDp < heightDp) {
            widthDp to heightDp
        } else {
            heightDp to widthDp
        }

        val (landscapeWidthDp, landscapeHeightDp) = portraitHeightDp to portraitWidthDp

        val screenType = when (portraitWidthDp) {
            in 0.0..360.0 -> ScreenType.SMALL
            in 360.0..480.0 -> ScreenType.DEFAULT
            in 480.0..600.0 -> ScreenType.LARGE
            else -> ScreenType.EXTRA_LARGE
        }

        return ScreenInfo(
            screenType = screenType,
            fontSizePrefs = FontSizePrefs.DEFAULT,
            portraitWidthDp = portraitWidthDp.toInt(),
            portraitHeightDp = portraitHeightDp.toInt(),
            landscapeWidthDp = landscapeWidthDp.toInt(),
            landscapeHeightDp = landscapeHeightDp.toInt()
        )
    }

}
