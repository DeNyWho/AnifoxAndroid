package club.anifox.android.feature.screenshots.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.screenshots.ScreenshotsScreen

const val ANIME_URL = "anime_url"
const val ANIME_TITLE = "anime_title"
const val SCREENSHOTS_ROUTE = "screenshots_route"

fun NavController.navigateToScreenshots(url: String, title: String, navOptions: NavOptions? = null) {
    val route = "${SCREENSHOTS_ROUTE}/${ANIME_URL}=$url&&${ANIME_TITLE}=$title"
    navigate(route, navOptions)
}

fun NavGraphBuilder.screenshotsScreen(
    onBackPressed: () -> Boolean,
) {
    composable(
        route = "$SCREENSHOTS_ROUTE/${ANIME_URL}={url}&&${ANIME_TITLE}={title}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
            navArgument("title") { type = NavType.StringType },
        ),
    ) {
        val url = remember { it.arguments?.getString("url") }
        val animeTitle = remember { it.arguments?.getString("title") }

        ScreenshotsScreen(
            url = url ?: "",
            animeTitle = animeTitle,
            onBackPressed = onBackPressed,
        )
    }
}