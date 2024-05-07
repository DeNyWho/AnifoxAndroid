package club.anifox.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import club.anifox.android.common.util.NetworkMonitor
import club.anifox.android.commonui.theme.AnifoxTheme
import club.anifox.android.ui.AnifoxApp
import club.anifox.android.ui.rememberAnifoxAppState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkMonitor: club.anifox.android.common.util.NetworkMonitor

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appState = rememberAnifoxAppState(
                windowSizeClass = calculateWindowSizeClass(this),
                networkMonitor = networkMonitor,
            )

            club.anifox.android.commonui.theme.AnifoxTheme {
                AnifoxApp(appState)
            }

        }
    }
}