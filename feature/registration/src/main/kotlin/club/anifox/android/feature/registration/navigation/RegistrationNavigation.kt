package club.anifox.android.feature.registration.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.registration.RegistrationScreen

const val REGISTRATION_ROUTE = "registration_route"

fun NavController.navigateToRegistration(navOptions: NavOptions) = navigate(REGISTRATION_ROUTE, navOptions)

fun NavGraphBuilder.registrationScreen() {
    composable(
        route = REGISTRATION_ROUTE
    ) {
        RegistrationScreen()
    }
}