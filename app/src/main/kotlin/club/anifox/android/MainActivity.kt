package club.anifox.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ScaffoldDefaults
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import club.anifox.android.common.util.network.NetworkMonitor
import club.anifox.android.commonui.theme.AnifoxTheme
import club.anifox.android.ui.AnifoxApp
import club.anifox.android.ui.rememberAnifoxAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        setContent {
            val appState = rememberAnifoxAppState(
                networkMonitor = networkMonitor,
            )

            AnifoxTheme {
                ScaffoldDefaults.contentWindowInsets
                AnifoxApp(appState)
            }

        }
    }
}