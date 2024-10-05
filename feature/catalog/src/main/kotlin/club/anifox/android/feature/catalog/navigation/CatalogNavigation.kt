package club.anifox.android.feature.catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.feature.catalog.CatalogScreen

const val CATALOG_ROUTE = "catalog_route"

fun NavController.navigateToCatalog(
    params: CatalogFilterParams,
    navOptions: NavOptions? = null
) {
    val genresParam = params.genres?.joinToString(",") ?: ""
    val statusParam = params.status?.name ?: ""
    val typeParam = params.type?.name ?: ""
    val yearParam = params.year?.toString() ?: ""
    val seasonParam = params.season?.name ?: ""
    val studioParam = params.studio?.joinToString(",") ?: ""

    val route = "${CATALOG_ROUTE}?genres=$genresParam&status=$statusParam&type=$typeParam&year=$yearParam&season=$seasonParam&studio=$studioParam"

    navigate(route, navOptions)
}

fun NavGraphBuilder.catalogScreen(
    onBackPressed: () -> Boolean,
    onSearchClick: () -> Unit,
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = "${CATALOG_ROUTE}?genres={genres}&status={status}&type={type}&year={year}&season={season}&studio={studio}",
        arguments = listOf(
            navArgument("genres") { type = NavType.StringType; defaultValue = "" },
            navArgument("status") { type = NavType.StringType; defaultValue = "" },
            navArgument("type") { type = NavType.StringType; defaultValue = "" },
            navArgument("year") { type = NavType.StringType; defaultValue = "" },
            navArgument("season") { type = NavType.StringType; defaultValue = "" },
            navArgument("studio") { type = NavType.StringType; defaultValue = "" },
        ),
    ) { backStackEntry ->
        val genres = backStackEntry.arguments?.getString("genres")?.let {
            if (it.isNotEmpty()) it.split(",").map { genre -> AnimeGenre(id = genre, name = genre) } else null
        }
        val status = backStackEntry.arguments?.getString("status")?.takeIf { it.isNotEmpty() }?.let { AnimeStatus.valueOf(it) }
        val type = backStackEntry.arguments?.getString("type")?.takeIf { it.isNotEmpty() }?.let { AnimeType.valueOf(it) }
        val year = backStackEntry.arguments?.getString("year")?.toIntOrNull()
        val season = backStackEntry.arguments?.getString("season")?.takeIf { it.isNotEmpty() }?.let { AnimeSeason.valueOf(it) }
        val studio = backStackEntry.arguments?.getString("studio")?.let {
            if (it.isNotEmpty()) it.split(",").map { studioName -> AnimeStudio(id = studioName, name = studioName) } else null
        }

        val params = CatalogFilterParams(
            genres = genres,
            status = status,
            type = type,
            year = year,
            season = season,
            studio = studio,
        )

        CatalogScreen(
            onBackPressed = onBackPressed,
            onSearchClick = onSearchClick,
            onAnimeClick = onAnimeClick,
            initialParams = params,
        )
    }
}