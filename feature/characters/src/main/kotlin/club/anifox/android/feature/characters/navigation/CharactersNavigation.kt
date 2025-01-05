package club.anifox.android.feature.characters.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.characters.CharactersScreen

const val CHARACTERS_ROUTE = "characters_route"
const val ANIME_TITLE = "anime_title"
const val ANIME_URL = "anime_url"

fun NavController.navigateToCharacters(url: String, title: String, navOptions: NavOptions? = null) {
    val route = "${CHARACTERS_ROUTE}/${ANIME_URL}=$url&${ANIME_TITLE}=$title"
    navigate(route, navOptions)
}

fun NavGraphBuilder.charactersScreen(
    onCharacterClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
) {
    composable(
        route = "$CHARACTERS_ROUTE/${ANIME_URL}={url}&${ANIME_TITLE}={title}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType },
        ),
    ) {
        val url = remember { it.arguments?.getString("url") }
        val animeTitle = remember { it.arguments?.getString("title") }

        CharactersScreen(
            url = url ?: throw IllegalArgumentException("CharactersScreen requires a non-null URL"),
            animeTitle = animeTitle ?: throw IllegalArgumentException("CharactersScreen requires a non-null title"),
            onCharacterClick = onCharacterClick,
            onBackPressed = onBackPressed,
        )
    }
}