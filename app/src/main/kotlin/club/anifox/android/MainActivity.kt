package club.anifox.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import club.anifox.android.core.common.util.network.NetworkMonitor
import club.anifox.android.core.uikit.theme.AnifoxTheme
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
        setContent {
            val isFirstLaunch by mainViewModel.isFirstLaunch.collectAsState()

            LaunchedEffect(isFirstLaunch) {
                if (isFirstLaunch != null) {
                    keepSplashScreen = false
                }
            }

            if (isFirstLaunch != null) {
                val appState = rememberAnifoxAppState(
                    networkMonitor = networkMonitor,
                    isFirstLaunch = isFirstLaunch!!,
                )
                AnifoxTheme {
                    ScaffoldDefaults.contentWindowInsets
                    AnifoxApp(appState)
                }
            }

        }
    }
}