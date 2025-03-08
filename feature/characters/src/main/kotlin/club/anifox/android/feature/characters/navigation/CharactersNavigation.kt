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
const val ANIME_URL = "anime_url"

fun NavController.navigateToCharacters(url: String, navOptions: NavOptions? = null) {
    val route = "${CHARACTERS_ROUTE}/${ANIME_URL}=$url"
    navigate(route, navOptions)
}

fun NavGraphBuilder.charactersScreen(
    onCharacterClick: (String) -> Unit,
    onBackPressed: () -> Unit,
) {
    composable(
        route = "$CHARACTERS_ROUTE/${ANIME_URL}={url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
        ),
    ) {
        val url = remember { it.arguments?.getString("url") }

        CharactersScreen(
            url = url ?: throw IllegalArgumentException("CharactersScreen requires a non-null URL"),
            onCharacterClick = onCharacterClick,
            onBackPressed = onBackPressed,
        )
    }
}