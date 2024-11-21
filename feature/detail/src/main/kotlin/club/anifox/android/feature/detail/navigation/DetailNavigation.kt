package club.anifox.android.feature.detail.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.feature.detail.DetailScreen

const val DETAIL_ROUTE_BASE = "detail_route"

fun NavController.navigateToDetail(url: String, navOptions: NavOptions? = null) = navigate("${DETAIL_ROUTE_BASE}/url=$url", navOptions)

fun NavGraphBuilder.detailScreen(
    onBackPressed: () -> Boolean,
    onWatchClick: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    onCatalogClick: (CatalogFilterParams) -> Unit,
    onMoreScreenshotClick: (String, String) -> Unit,
    onMoreVideoClick: (String, String) -> Unit,
) {
    composable(
        "$DETAIL_ROUTE_BASE/url={url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
        ),
    ) {
        val url = remember { it.arguments?.getString("url") }

        DetailScreen(
            url = url ?: "",
            onBackPressed = onBackPressed,
            onWatchClick = onWatchClick,
            onAnimeClick = onAnimeClick,
            onMoreScreenshotClick = onMoreScreenshotClick,
            onMoreVideoClick = onMoreVideoClick,
            onCatalogClick = onCatalogClick,
        )
    }
}