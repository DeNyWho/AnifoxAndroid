package club.anifox.android.feature.video.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import club.anifox.android.feature.video.VideoScreen

const val VIDEO_ROUTE = "video_route"

fun NavController.navigateToVideo(navOptions: NavOptions) = navigate(VIDEO_ROUTE, navOptions)

fun NavGraphBuilder.videoScreen() {
    composable(
        route = VIDEO_ROUTE
    ) {
        VideoScreen()
    }
}