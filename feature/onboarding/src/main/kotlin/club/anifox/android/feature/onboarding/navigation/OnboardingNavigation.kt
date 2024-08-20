package club.anifox.android.feature.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.onboarding.OnboardingScreen

const val ONBOARDING_ROUTE = "onboarding_route"

fun NavController.navigateToOnboarding(navOptions: NavOptions) = navigate(ONBOARDING_ROUTE, navOptions)

fun NavGraphBuilder.onboardingScreen() {
    composable(
        route = ONBOARDING_ROUTE
    ) {
        OnboardingScreen()
    }
}