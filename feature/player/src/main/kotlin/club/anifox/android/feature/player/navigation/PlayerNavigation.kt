package club.anifox.android.feature.player.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.player.PlayerScreen
import java.net.URLDecoder

const val PLAYER_ROUTE = "player_route"

fun NavController.navigateToPlayer(url: String, kodik: Boolean?, navOptions: NavOptions? = null) {
    navigate("$PLAYER_ROUTE?url=$url&kodik=$kodik", navOptions)
}

fun NavGraphBuilder.playerScreen(onBackPressed: () -> Unit) {
    composable(
        route = "$PLAYER_ROUTE?url={url}&kodik={kodik}",
        arguments = listOf(
            navArgument("url") {
                type = NavType.StringType
                nullable = false
            },
            navArgument("kodik") {
                type = NavType.BoolType
                defaultValue = false
            }
        )
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString("url")
        val kodik = backStackEntry.arguments?.getBoolean("kodik") ?: false

        val decodedUrl = URLDecoder.decode(url, "UTF-8")

        PlayerScreen(
            onBackPressed = onBackPressed,
            url = decodedUrl,
            kodik = kodik
        )
    }
}