package club.anifox.android.feature.browse.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.browse.BrowseScreen

const val BROWSE_ROUTE = "browse_route"

fun NavController.navigateToBrowse(navOptions: NavOptions) = navigate(BROWSE_ROUTE, navOptions)

fun NavGraphBuilder.browseScreen() {
    composable(
        route = BROWSE_ROUTE
    ) {
        BrowseScreen()
    }
}