package club.anifox.android.feature.translations.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.translations.TranslationsScreen

const val TRANSLATIONS_ROUTE = "translations_route"

fun NavController.navigateToTranslations(navOptions: NavOptions) = navigate(TRANSLATIONS_ROUTE, navOptions)

fun NavGraphBuilder.translationsScreen(
    onBackPressed: () -> Boolean,
) {
    composable(
        route = TRANSLATIONS_ROUTE
    ) {
        TranslationsScreen(
            onBackPressed = onBackPressed,
        )
    }
}