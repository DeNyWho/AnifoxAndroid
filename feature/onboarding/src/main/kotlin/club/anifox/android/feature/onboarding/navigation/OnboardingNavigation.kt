package club.anifox.android.feature.onboarding.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import club.anifox.android.feature.onboarding.OnboardingScreen

const val ONBOARDING_ROUTE = "onboarding_route"

fun NavGraphBuilder.onboardingScreen(
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    composable(
        route = ONBOARDING_ROUTE
    ) {
        OnboardingScreen(
            onLoginClick = onLoginClick,
            onRegistrationClick = onRegistrationClick,
        )
    }
}