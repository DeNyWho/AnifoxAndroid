package club.anifox.android.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.domain.model.common.device.ThemeType
import club.anifox.android.feature.settings.component.player.PlayerComponent
import club.anifox.android.feature.settings.component.theme.ThemeComponent

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
) {
    val selectedThemeState by viewModel.selectedTheme.collectAsState()
    val selectedPlayerOrientationState by viewModel.selectedPlayerOrientation.collectAsState()

    SettingsUI(
        onBackPressed = onBackPressed,
        selectedThemeState = selectedThemeState,
        selectedPlayerOrientationState = selectedPlayerOrientationState,
        updateTheme = { theme ->
            viewModel.updateThemeSettings(theme)
        },
        updatePlayerOrientation = {
            viewModel.updatePlayerOrientation()
        },
    )
}

@Composable
private fun SettingsUI(
    onBackPressed: () -> Boolean,
    selectedThemeState: ThemeType,
    selectedPlayerOrientationState: PlayerOrientation,
    updateTheme: (ThemeType) -> Unit,
    updatePlayerOrientation: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SimpleTopBar(
                onBackPressed = onBackPressed,
                title = stringResource(R.string.feature_settings_top_bar_title),
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
            )
        },
    ) { padding ->
        SettingsContentUI(
            modifier = Modifier
                .padding(padding),
            selectedThemeState = selectedThemeState,
            selectedPlayerOrientationState = selectedPlayerOrientationState,
            updateTheme = updateTheme,
            updatePlayerOrientation = updatePlayerOrientation,
        )
    }
}

@Composable
private fun SettingsContentUI(
    modifier: Modifier = Modifier,
    selectedThemeState: ThemeType,
    selectedPlayerOrientationState: PlayerOrientation,
    updateTheme: (ThemeType) -> Unit,
    updatePlayerOrientation: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp),
    ) {
        ThemeComponent(
            selectedThemeState = selectedThemeState,
            updateThemeStatus = updateTheme,
        )

        Spacer(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                )
                .height(1.dp)
                .background(Color.LightGray.copy(0.1f))
                .fillMaxWidth(),
        )

        PlayerComponent(
            selectedPlayerOrientationState = selectedPlayerOrientationState,
            updatePlayerOrientation = updatePlayerOrientation,
        )
    }
}