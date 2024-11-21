package club.anifox.android.navigation

import androidx.annotation.DrawableRes
import club.anifox.android.R

enum class TopLevelDestination(
    @DrawableRes val icon: Int,
    val iconTextId: Int,
) {
    HOME(
        icon = R.drawable.home,
        iconTextId = R.string.navigation_bar_home_title,
    ),
//    BROWSE(
//        icon = R.drawable.search,
//        iconTextId = R.string.navigation_bar_browse_title,
//    ),
    SCHEDULE(
      icon = R.drawable.calendar,
        iconTextId = R.string.navigation_bar_schedule_title,
    ),
    FAVOURITE(
        icon = R.drawable.bookmark,
        iconTextId = R.string.navigation_bar_favourite_title,
    ),
//    PROFILE(
//        icon = R.drawable.profile,
//        iconTextId = R.string.navigation_bar_profile_title,
//    ),
}