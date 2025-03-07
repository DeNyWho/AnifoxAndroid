package club.anifox.android.feature.catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
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
    val yearsParam = params.years?.joinToString(",") ?: ""
    val seasonParam = params.season?.name ?: ""
    val studioParam = params.studios?.joinToString(",") ?: ""
    val orderParam = params.order?.name ?: ""
    val sortParam = params.sort?.name ?: ""

    val route =
        "${CATALOG_ROUTE}?genres=$genresParam&status=$statusParam&type=$typeParam&years=$yearsParam&season=$seasonParam&studios=$studioParam&order=$orderParam&sort=$sortParam"

    navigate(route, navOptions)
}

fun NavGraphBuilder.catalogScreen(
    onBackPressed: () -> Unit,
    onSearchClick: () -> Unit,
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = "${CATALOG_ROUTE}?genres={genres}&status={status}&type={type}&years={years}&season={season}&studios={studios}&order={order}&sort={sort}",
        arguments = listOf(
            navArgument("genres") { type = NavType.StringType; defaultValue = "" },
            navArgument("status") { type = NavType.StringType; defaultValue = "" },
            navArgument("type") { type = NavType.StringType; defaultValue = "" },
            navArgument("years") { type = NavType.StringType; defaultValue = "" },
            navArgument("season") { type = NavType.StringType; defaultValue = "" },
            navArgument("studios") { type = NavType.StringType; defaultValue = "" },
            navArgument("order") { type = NavType.StringType; defaultValue = "" },
            navArgument("sort") { type = NavType.StringType; defaultValue = "" },
        ),
    ) { backStackEntry ->
        val genres = backStackEntry.arguments?.getString("genres")?.let { genresString ->
            if (genresString.isNotEmpty()) {
                val pattern = "AnimeGenre\\(id=([^,]+), name=([^)]+)\\)".toRegex()
                pattern.findAll(genresString).map { matchResult ->
                    val (id, name) = matchResult.destructured
                    AnimeGenre(id = id, name = name)
                }.toList()
            } else null
        }
        val status = backStackEntry.arguments?.getString("status")?.takeIf { it.isNotEmpty() }
            ?.let { AnimeStatus.valueOf(it) }
        val type = backStackEntry.arguments?.getString("type")?.takeIf { it.isNotEmpty() }
            ?.let { AnimeType.valueOf(it) }
        val years = backStackEntry.arguments?.getString("years")?.takeIf { it.isNotEmpty() }
            ?.let { yearsString ->
                yearsString.split(",").mapNotNull { yearStr ->
                    yearStr.toIntOrNull()
                }
            }
        val season = backStackEntry.arguments?.getString("season")?.takeIf { it.isNotEmpty() }
            ?.let { AnimeSeason.valueOf(it) }
        val studios = backStackEntry.arguments?.getString("studios")?.let { studiosString ->
            if (studiosString.isNotEmpty()) {
                val pattern = "AnimeStudio\\(id=([^,]+), name=([^)]+)\\)".toRegex()
                pattern.findAll(studiosString).map { matchResult ->
                    val (id, name) = matchResult.destructured
                    AnimeStudio(id = id, name = name)
                }.toList()
            } else null
        }
        val order = backStackEntry.arguments?.getString("order")?.takeIf { it.isNotEmpty() }
            ?.let { AnimeOrder.valueOf(it) }
        val sort = backStackEntry.arguments?.getString("sort")?.takeIf { it.isNotEmpty() }
            ?.let { AnimeSort.valueOf(it) }

        val params = CatalogFilterParams(
            genres = genres,
            status = status,
            type = type,
            years = years,
            season = season,
            studios = studios,
            order = order,
            sort = sort,
        )

        CatalogScreen(
            onBackPressed = onBackPressed,
            onSearchClick = onSearchClick,
            onAnimeClick = onAnimeClick,
            initialParams = params,
        )
    }
}