package club.anifox.android.feature.search.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.search.SearchScreen

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) = navigate(SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen(
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = SEARCH_ROUTE
    ) {
        SearchScreen(
            onBackPressed = onBackPressed,
            onAnimeClick = onAnimeClick,
        )
    }
}