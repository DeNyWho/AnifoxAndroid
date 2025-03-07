package club.anifox.android.feature.translations.navigation

import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import club.anifox.android.feature.translations.TranslationsScreen

const val TRANSLATIONS_ROUTE = "translations_route"

fun NavController.navigateToTranslations(url: String, navOptions: NavOptions? = null) =
    navigate("${TRANSLATIONS_ROUTE}?url=$url", navOptions)

fun NavGraphBuilder.translationsScreen(
    onBackPressed: () -> Unit,
    onTranslationClick: (String, Int) -> Unit,
    onPlayerClick: (String, Boolean?) -> Unit,
) {
    composable(
        "$TRANSLATIONS_ROUTE?url={url}",
        arguments = listOf(
            navArgument("url") { type = NavType.StringType },
        ),
    ) {
        val url = remember { it.arguments?.getString("url") }

        TranslationsScreen(
            onBackPressed = onBackPressed,
            url = url ?: "",
            onTranslationClick = onTranslationClick,
            onPlayerClick = onPlayerClick,
        )
    }
}