package club.anifox.android.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.detail.DetailRoute

const val ANIME_URL = "animeUrl"
const val DETAIL_ROUTE_BASE = "detail_route"
const val DETAIL_ROUTE = "${DETAIL_ROUTE_BASE}/${ANIME_URL}={$ANIME_URL}"

fun NavController.navigateToDetail(url: String? = null, navOptions: NavOptions? = null) {
    val route = if (url != null) {
        "${DETAIL_ROUTE_BASE}/${ANIME_URL}=$url"
    } else {
        DETAIL_ROUTE_BASE
    }
    navigate(route, navOptions)
}

fun NavGraphBuilder.detailScreen() {
    composable(
        route = DETAIL_ROUTE
    ) {
        DetailRoute()
    }
}