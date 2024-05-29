package club.anifox.android.feature.detail.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.detail.DetailScreen

const val ANIME_URL = "animeUrl"
const val DETAIL_ROUTE_BASE = "detail_route"

fun NavController.navigateToDetail(url: String? = null, navOptions: NavOptions? = null) {
    val route = if (url != null) {
        "${DETAIL_ROUTE_BASE}/${ANIME_URL}=$url"
    } else {
        DETAIL_ROUTE_BASE
    }
    navigate(route, navOptions)
}

fun NavGraphBuilder.detailScreen(onBackPressed: () -> Boolean) {
    composable(
        "$DETAIL_ROUTE_BASE/${ANIME_URL}={url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
        )
    ) {
        val url = remember { it.arguments?.getString("url") }

        DetailScreen(
            url = url ?: "",
            onBackPressed = onBackPressed,
        )
    }
}