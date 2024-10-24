package club.anifox.android.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import club.anifox.android.feature.catalog.navigation.catalogScreen
import club.anifox.android.feature.catalog.navigation.navigateToCatalog
import club.anifox.android.feature.detail.navigation.detailScreen
import club.anifox.android.feature.detail.navigation.navigateToDetail
import club.anifox.android.feature.favourite.navigation.favouriteScreen
import club.anifox.android.feature.genres.navigation.genresScreen
import club.anifox.android.feature.genres.navigation.navigateToGenres
import club.anifox.android.feature.home.navigation.HOME_ROUTE
import club.anifox.android.feature.home.navigation.homeScreen
import club.anifox.android.feature.login.navigation.loginScreen
import club.anifox.android.feature.login.navigation.navigateToLogin
import club.anifox.android.feature.onboarding.navigation.ONBOARDING_ROUTE
import club.anifox.android.feature.onboarding.navigation.onboardingScreen
import club.anifox.android.feature.profile.navigation.profileScreen
import club.anifox.android.feature.registration.navigation.navigateToRegistration
import club.anifox.android.feature.registration.navigation.registrationScreen
import club.anifox.android.feature.schedule.navigation.scheduleScreen
import club.anifox.android.feature.screenshots.navigation.navigateToScreenshots
import club.anifox.android.feature.screenshots.navigation.screenshotsScreen
import club.anifox.android.feature.search.navigation.navigateToSearch
import club.anifox.android.feature.search.navigation.searchScreen
import club.anifox.android.feature.translations.navigation.navigateToTranslations
import club.anifox.android.feature.translations.navigation.translationsScreen
import club.anifox.android.feature.video.navigation.navigateToVideo
import club.anifox.android.feature.video.navigation.videoScreen
import club.anifox.android.ui.AnifoxAppState

@Composable
fun AnifoxNavHost(
    appState: AnifoxAppState,
    modifier: Modifier = Modifier,
    startDestination: String = HOME_ROUTE,
    isFirstLaunch: Boolean,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = if(isFirstLaunch) ONBOARDING_ROUTE else startDestination,
        modifier = modifier,
    ) {
        homeScreen(
            onAnimeClick = navController::navigateToDetail,
            onSearchClick = navController::navigateToSearch,
            onGenresClick = navController::navigateToGenres,
            onMoreClick = navController::navigateToCatalog,
        )
        detailScreen(
            onBackPressed = navController::popBackStack,
            onWatchClick = navController::navigateToTranslations,
            onAnimeClick = navController::navigateToDetail,
            onMoreScreenshotClick = navController::navigateToScreenshots,
            onMoreVideoClick = navController::navigateToVideo,
            onCatalogClick = navController::navigateToCatalog,
        )
        translationsScreen(
            onBackPressed = navController::popBackStack,
        )
        searchScreen(
            onBackPressed = navController::popBackStack,
            onAnimeClick = navController::navigateToDetail,
        )
        screenshotsScreen(
            onBackPressed = navController::popBackStack,
        )
        videoScreen(
            onBackPressed = navController::popBackStack,
        )
        scheduleScreen()
        profileScreen(
            onLoginClick = navController::navigateToLogin,
            onRegistrationClick = navController::navigateToRegistration,
        )
        favouriteScreen(
            onLoginClick = navController::navigateToLogin,
            onRegistrationClick = navController::navigateToRegistration,
        )
        loginScreen()
        registrationScreen()
        genresScreen(
            onAnimeClick = navController::navigateToDetail,
            onBackPressed = navController::popBackStack,
        )
        catalogScreen(
            onAnimeClick = navController::navigateToDetail,
            onSearchClick = navController::navigateToSearch,
            onBackPressed = navController::popBackStack,
        )
        onboardingScreen(
            onLoginClick = navController::navigateToLogin,
            onRegistrationClick = navController::navigateToRegistration,
        )
    }
}