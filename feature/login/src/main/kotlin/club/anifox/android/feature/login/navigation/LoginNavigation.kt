package club.anifox.android.feature.login.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.login.LoginScreen

const val LOGIN_ROUTE = "login_route"

fun NavController.navigateToLogin(navOptions: NavOptions? = null) =
    navigate(LOGIN_ROUTE, navOptions)

fun NavGraphBuilder.loginScreen() {
    composable(
        route = LOGIN_ROUTE
    ) {
        LoginScreen()
    }
}