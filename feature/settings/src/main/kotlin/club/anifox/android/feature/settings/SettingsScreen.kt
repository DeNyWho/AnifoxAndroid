package club.anifox.android.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.domain.model.common.device.ThemeType
import club.anifox.android.feature.settings.component.theme.ThemeComponent

@Composable
internal fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
) {
    val selectedThemeState by viewModel.selectedTheme.collectAsState()

    SettingsUI(
        onBackPressed = onBackPressed,
        selectedThemeState = selectedThemeState,
        updateTheme = { theme ->
            viewModel.updateThemeSettings(theme)
        }
    )
}

@Composable
private fun SettingsUI(
    onBackPressed: () -> Boolean,
    selectedThemeState: ThemeType,
    updateTheme: (ThemeType) -> Unit,
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
            updateTheme = updateTheme,
        )
    }
}

@Composable
private fun SettingsContentUI(
    modifier: Modifier = Modifier,
    selectedThemeState: ThemeType,
    updateTheme: (ThemeType) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(top = 16.dp),
    ) {
        ThemeComponent(
            selectedThemeState = selectedThemeState,
            updateThemeStatus = updateTheme,
        )
    }
}

//@PreviewScreenSizes
//@Composable
//private fun PreviewSettingsUI(
//    @PreviewParameter(SettingsUIProvider::class) param: SettingsUIPreviewParam,
//) {
//    DefaultPreview(true) {
//        SettingsUI(
//            onBackPressed = param.onBackPressed,
//        )
//    }
//}
