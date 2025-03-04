package club.anifox.android.feature.search.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.search.SearchScreen
import club.anifox.android.feature.search.SearchViewModel

const val SEARCH_ROUTE = "search_route"

fun NavController.navigateToSearch(navOptions: NavOptions? = null) = navigate(SEARCH_ROUTE, navOptions)

fun NavGraphBuilder.searchScreen(
    onBackPressed: () -> Unit,
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = SEARCH_ROUTE
    ) {
        val viewModel: SearchViewModel = hiltViewModel()
        SearchScreen(
            viewModel = viewModel,
            onBackPressed = onBackPressed,
            onAnimeClick = onAnimeClick,
        )
    }
}