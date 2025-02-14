package club.anifox.android.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import club.anifox.android.feature.settings.component.lic.LicComponent
import club.anifox.android.feature.settings.component.links.LinksComponent
import club.anifox.android.feature.settings.component.player.PlayerComponent
import club.anifox.android.feature.settings.component.theme.ThemeComponent

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
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
        openLicHolders = {
            viewModel.openWeb(BuildConfig.url_holders)
        },
        openOffSuite = {
            viewModel.openWeb(BuildConfig.url_off_suite)
        },
        openTelegram = {
            viewModel.openTelegram(BuildConfig.url_off_telegram)
        },
    )
}

@Composable
private fun SettingsUI(
    onBackPressed: () -> Unit,
    selectedThemeState: ThemeType,
    selectedPlayerOrientationState: PlayerOrientation,
    updateTheme: (ThemeType) -> Unit,
    updatePlayerOrientation: () -> Unit,
    openLicHolders: () -> Unit,
    openOffSuite: () -> Unit,
    openTelegram: () -> Unit,
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
            openLicHolders = openLicHolders,
            openOffSuite = openOffSuite,
            openTelegram = openTelegram,
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
    openLicHolders: () -> Unit,
    openOffSuite: () -> Unit,
    openTelegram: () -> Unit,
) {
    val settingsItems = listOf<@Composable () -> Unit>(
        {
            ThemeComponent(
                selectedThemeState = selectedThemeState,
                updateThemeStatus = updateTheme,
            )
        },
        {
            PlayerComponent(
                selectedPlayerOrientationState = selectedPlayerOrientationState,
                updatePlayerOrientation = updatePlayerOrientation,
            )
        },
        {
            LicComponent(
                openLicHolders = openLicHolders,
            )
        },
        {
            LinksComponent(
                openOffSuite = openOffSuite,
                openTelegram = openTelegram,
            )
        },
    )
    val lazyColumnState = rememberLazyListState()

    LazyColumn(
        modifier = modifier
            .padding(top = 16.dp),
        state = lazyColumnState,
    ) {
        settingsItems.forEachIndexed { index, item ->
            item {
                item()
                if (index < settingsItems.size - 1) {
                    Spacer(
                        modifier = Modifier
                            .padding(
                                vertical = 8.dp,
                            )
                            .height(1.dp)
                            .background(Color.LightGray.copy(0.1f))
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}