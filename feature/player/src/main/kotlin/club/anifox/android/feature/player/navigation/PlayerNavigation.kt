package club.anifox.android.feature.player.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.player.PlayerScreen
import java.net.URLDecoder

const val PLAYER_ROUTE = "player_route"

fun NavController.navigateToPlayer(url: String, navOptions: NavOptions? = null) = navigate("${PLAYER_ROUTE}/url=$url", navOptions)

fun NavGraphBuilder.playerScreen(
    onBackPressed: () -> Unit,
) {
    composable(
        "$PLAYER_ROUTE/url={url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
        ),
    ) {
        val url = remember { URLDecoder.decode(it.arguments?.getString("url"), "UTF-8") }

        PlayerScreen(
            onBackPressed = onBackPressed,
            url = url ?: throw IllegalArgumentException("PlayerScreen requires a non-null URL"),
        )
    }
}