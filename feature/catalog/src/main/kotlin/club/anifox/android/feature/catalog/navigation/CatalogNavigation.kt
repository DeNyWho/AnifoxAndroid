package club.anifox.android.feature.catalog.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.catalog.CatalogScreen

const val CATALOG_ROUTE = "catalog_route"

fun NavController.navigateToCatalog(navOptions: NavOptions) = navigate(CATALOG_ROUTE, navOptions)

fun NavGraphBuilder.catalogScreen(
    onBackPressed: () -> Boolean,
) {
    composable(
        route = CATALOG_ROUTE
    ) {
        CatalogScreen(
            onBackPressed = onBackPressed,
        )
    }
}