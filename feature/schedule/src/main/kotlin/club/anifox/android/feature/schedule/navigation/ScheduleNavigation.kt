package club.anifox.android.feature.schedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.schedule.ScheduleScreen

const val SCHEDULE_ROUTE = "schedule_route"

fun NavController.navigateToSchedule(navOptions: NavOptions) = navigate(SCHEDULE_ROUTE, navOptions)

fun NavGraphBuilder.scheduleScreen() {
    composable(
        route = SCHEDULE_ROUTE
    ) {
        ScheduleScreen()
    }
}