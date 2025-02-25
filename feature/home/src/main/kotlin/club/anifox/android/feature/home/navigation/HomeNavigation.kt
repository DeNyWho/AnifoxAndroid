package club.anifox.android.feature.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.feature.home.HomeScreen

const val HOME_ROUTE = "home_route"

fun NavController.navigateToHome(navOptions: NavOptions? = null) = navigate(HOME_ROUTE, navOptions)

fun NavGraphBuilder.homeScreen(
    onAnimeClick: (String) -> Unit,
    onSearchClick: () -> Unit,
    onGenresClick: (AnimeGenre) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    onSettingsClick: () -> Unit,
) {
    composable(
        route = HOME_ROUTE
    ) {
        HomeScreen(
            onAnimeClick = onAnimeClick,
            onSearchClick = onSearchClick,
            onGenresClick = onGenresClick,
            onCatalogClick = onCatalogClick,
            onSettingsClick = onSettingsClick,
        )
    }
}