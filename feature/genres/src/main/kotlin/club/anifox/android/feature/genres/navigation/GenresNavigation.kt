package club.anifox.android.feature.genres.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.genres.GenresScreen

private const val GENRE_ID = "genre_id"
const val GENRES_ROUTE = "genres_route"

fun NavController.navigateToGenres(genreID: String, navOptions: NavOptions? = null) = navigate("$GENRES_ROUTE/$GENRE_ID=$genreID", navOptions)

fun NavGraphBuilder.genresScreen(
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
) {
    composable(
        route = "$GENRES_ROUTE/$GENRE_ID={genreID}",
        arguments = listOf(
            navArgument("genreID") { type = NavType.StringType },
        ),
    ) {
        val genreID = remember { it.arguments?.getString("genreID") } ?: ""
        GenresScreen(
            genreID = genreID,
            onAnimeClick = onAnimeClick,
            onBackPressed = onBackPressed,
        )
    }
}