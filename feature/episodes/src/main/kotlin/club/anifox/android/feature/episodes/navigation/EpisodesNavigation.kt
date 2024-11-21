package club.anifox.android.feature.episodes.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.episodes.EpisodesScreen

const val EPISODES_ROUTE = "episodes_route"

fun NavController.navigateToEpisodes(url: String, translationId: Int, navOptions: NavOptions? = null) = navigate("$EPISODES_ROUTE?url=$url&translation_id=$translationId", navOptions)

fun NavGraphBuilder.episodesScreen(
    onBackPressed: () -> Boolean,
//    onEpisodeClick: (String) -> Unit,
) {
    composable(
        "$EPISODES_ROUTE?url={url}&translation_id={translation_id}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
            navArgument("translation_id") { type = NavType.IntType },
        ),
    ) {
        val url = remember { it.arguments?.getString("url") }
        val translationId = remember { it.arguments?.getInt("translation_id")}

        EpisodesScreen(
            onBackPressed = onBackPressed,
            url = url ?: "",
            translationId = translationId ?: 0,
//            onEpisodeClick = onEpisodeClick,
        )
    }
}