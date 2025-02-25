package club.anifox.android.feature.genres.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.feature.genres.GenresScreen
import kotlinx.serialization.json.Json

private const val GENRE_ID = "genre_id"
const val GENRES_ROUTE = "genres_route"

fun NavController.navigateToGenres(genre: AnimeGenre, navOptions: NavOptions? = null) {
    val genreJson = Json.encodeToString(genre)
    navigate("$GENRES_ROUTE?$GENRE_ID=$genreJson", navOptions)
}

fun NavGraphBuilder.genresScreen(
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Unit,
) {
    composable(
        route = "$GENRES_ROUTE?$GENRE_ID={genreID}",
        arguments = listOf(
            navArgument("genreID") { type = NavType.StringType },
        ),
    ) {
        val genreJson = remember { it.arguments?.getString("genreID") } ?: ""
        val genre = runCatching { Json.decodeFromString<AnimeGenre>(genreJson) }.getOrNull() ?: AnimeGenre()

        GenresScreen(
            genre = genre,
            onAnimeClick = onAnimeClick,
            onBackPressed = onBackPressed,
        )
    }
}