package club.anifox.android.domain.model.common.device

data class ScreenInfo(
    val screenType: ScreenType = ScreenType.DEFAULT,
    val fontSizePrefs: FontSizePrefs = FontSizePrefs.DEFAULT,
    val portraitWidthDp: Int = 400,
    val portraitHeightDp: Int = 800,
)