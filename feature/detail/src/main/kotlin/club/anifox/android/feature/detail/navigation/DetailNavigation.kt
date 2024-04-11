package club.anifox.android.feature.detail.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.detail.DetailRoute

const val ANIME_URL = "animeUrl"
const val DETAIL_ROUTE = "detail_route/${ANIME_URL}"

fun NavController.navigateToDetail(navOptions: NavOptions) = navigate(DETAIL_ROUTE, navOptions)

fun NavGraphBuilder.detailScreen() {
    composable(
        route = DETAIL_ROUTE
    ) {
        DetailRoute()
    }
}