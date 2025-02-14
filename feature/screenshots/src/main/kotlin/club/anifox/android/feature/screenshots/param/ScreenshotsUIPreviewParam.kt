package club.anifox.android.feature.screenshots.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.state.StateListWrapper

internal data class ScreenshotsUIPreviewParam(
    val modifier: Modifier = Modifier,
    val screenshotsAnime: StateListWrapper<String>,
    val onBackPressed: () -> Unit = { },
    val animeTitle: String?,
)

internal class ScreenshotsUIProvider:
    PreviewParameterProvider<ScreenshotsUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<ScreenshotsUIPreviewParam>
        get() = listOf(
            ScreenshotsUIPreviewParam(
                modifier = Modifier,
                screenshotsAnime = StateListWrapper(isLoading = false),
                animeTitle = "Провожающая в последний путь Фрирен",
            ),
            ScreenshotsUIPreviewParam(
                modifier = Modifier,
                screenshotsAnime = StateListWrapper(isLoading = true),
                animeTitle = "Провожающая в последний путь Фрирен",
            ),
        ).asSequence()
}