package club.anifox.android.feature.character.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.character.CharacterScreen

const val CHARACTER_ROUTE = "character_route"

fun NavController.navigateToCharacter(id: String, navOptions: NavOptions? = null) =
    navigate("${CHARACTER_ROUTE}?id=$id", navOptions)

fun NavGraphBuilder.characterScreen(
    onBackPressed: () -> Unit,
    onAnimeClick: (String) -> Unit,
) {
    composable(
        route = "${CHARACTER_ROUTE}?id={id}",
        arguments = listOf(
            navArgument("id") { type = NavType.StringType },
        ),
    ) {
        val id = remember { it.arguments?.getString("id") }

        CharacterScreen(
            onBackPressed = onBackPressed,
            onAnimeClick = onAnimeClick,
            id = id ?: throw IllegalArgumentException("CharacterScreen requires a non-null ID"),
        )
    }
}