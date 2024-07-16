package club.anifox.android.feature.screenshots.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.screenshots.ScreenshotsScreen

const val SCREENSHOTS_ROUTE = "screenshots_route"

fun NavController.navigateToScreenshots(navOptions: NavOptions) = navigate(SCREENSHOTS_ROUTE, navOptions)

fun NavGraphBuilder.screenshotsScreen() {
    composable(
        route = SCREENSHOTS_ROUTE
    ) {
        ScreenshotsScreen()
    }
}