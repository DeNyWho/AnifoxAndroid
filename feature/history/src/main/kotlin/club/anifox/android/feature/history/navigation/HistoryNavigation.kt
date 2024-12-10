package club.anifox.android.feature.history.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.history.HistoryScreen

const val HISTORY_ROUTE = "history_route"

fun NavController.navigateToHistory(navOptions: NavOptions? = null) = navigate(HISTORY_ROUTE, navOptions)

fun NavGraphBuilder.historyScreen(
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = HISTORY_ROUTE
    ) {
        HistoryScreen(
            onBackPressed = onBackPressed,
            onAnimeClick = onAnimeClick,
        )
    }
}