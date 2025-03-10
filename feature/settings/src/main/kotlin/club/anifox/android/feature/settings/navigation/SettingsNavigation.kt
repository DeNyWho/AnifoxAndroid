package club.anifox.android.feature.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.settings.SettingsScreen

const val SETTINGS_ROUTE = "settings_route"

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    navigate(SETTINGS_ROUTE, navOptions)

fun NavGraphBuilder.settingsScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        route = SETTINGS_ROUTE
    ) {
        SettingsScreen(
            onBackPressed = onBackPressed,
        )
    }
}