package club.anifox.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import club.anifox.android.feature.detail.navigation.detailScreen
import club.anifox.android.feature.detail.navigation.navigateToDetail
import club.anifox.android.feature.favourite.navigation.favouriteScreen
import club.anifox.android.feature.home.navigation.HOME_ROUTE
import club.anifox.android.feature.home.navigation.homeScreen
import club.anifox.android.feature.login.navigation.loginScreen
import club.anifox.android.feature.profile.navigation.profileScreen
import club.anifox.android.feature.schedule.navigation.scheduleScreen
import club.anifox.android.feature.screenshots.navigation.navigateToScreenshots
import club.anifox.android.feature.screenshots.navigation.screenshotsScreen
import club.anifox.android.feature.search.navigation.navigateToSearch
import club.anifox.android.feature.search.navigation.searchScreen
import club.anifox.android.feature.video.navigation.navigateToVideo
import club.anifox.android.feature.video.navigation.videoScreen
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
        homeScreen(
            onAnimeClick = navController::navigateToDetail,
            onSearchClick = navController::navigateToSearch,
        )
        detailScreen(
            onBackPressed = navController::popBackStack,
            onAnimeClick = navController::navigateToDetail,
            onMoreScreenshotClick = navController::navigateToScreenshots,
            onMoreVideoClick = navController::navigateToVideo,
        )
        searchScreen(
            onBackPressed = navController::popBackStack,
            onAnimeClick = navController::navigateToDetail
        )
        screenshotsScreen(
            onBackPressed = navController::popBackStack,
        )
        videoScreen(
            onBackPressed = navController::popBackStack,
        )
        scheduleScreen()
        profileScreen()
//        browseScreen()
        favouriteScreen()
        loginScreen()
    }
}