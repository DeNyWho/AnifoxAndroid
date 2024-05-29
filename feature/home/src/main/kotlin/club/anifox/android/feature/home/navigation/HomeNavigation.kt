package club.anifox.android.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.home.HomeScreen
import club.anifox.android.feature.home.HomeUI

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreen(onAnimeClick = onAnimeClick)
    }
}