package club.anifox.android.feature.favourite.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.favourite.FavouriteScreen

const val FAVOURITE_ROUTE = "favourite_route"

fun NavController.navigateToFavourite(navOptions: NavOptions) = navigate(FAVOURITE_ROUTE, navOptions)

fun NavGraphBuilder.favouriteScreen(
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    composable(
        route = FAVOURITE_ROUTE
    ) {
        FavouriteScreen(
            onLoginClick = onLoginClick,
            onRegistrationClick = onRegistrationClick,
        )
    }
}