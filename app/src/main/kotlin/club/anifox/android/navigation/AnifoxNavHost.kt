package club.anifox.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import club.anifox.android.feature.detail.navigation.detailScreen
import club.anifox.android.feature.detail.navigation.navigateToDetail
import club.anifox.android.feature.home.navigation.HOME_ROUTE
import club.anifox.android.feature.home.navigation.homeScreen
import club.anifox.android.feature.search.navigation.searchScreen
import club.anifox.android.ui.AnifoxAppState

@Composable
fun AnifoxNavHost(
    appState: AnifoxAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeScreen(onAnimeClick = navController::navigateToDetail)
        detailScreen(
            onBackPressed = navController::popBackStack,
            onAnimeClick = navController::navigateToDetail,
        )
        searchScreen()
    }
}