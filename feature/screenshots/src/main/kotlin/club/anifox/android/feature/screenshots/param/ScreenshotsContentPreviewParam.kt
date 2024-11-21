package club.anifox.android.feature.screenshots.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.state.StateListWrapper

internal data class ScreenshotsContentPreviewParam(
    val modifier: Modifier = Modifier,
    val screenshotsAnime: StateListWrapper<String>,
    val onBackPressed: () -> Boolean = { false },
    val animeTitle: String?,
)

internal class ScreenshotsContentProvider:
    PreviewParameterProvider<ScreenshotsContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<ScreenshotsContentPreviewParam>
        get() = listOf(
            ScreenshotsContentPreviewParam(
                modifier = Modifier,
                screenshotsAnime = StateListWrapper(isLoading = false),
                animeTitle = "Провожающая в последний путь Фрирен",
            ),
            ScreenshotsContentPreviewParam(
                modifier = Modifier,
                screenshotsAnime = StateListWrapper(isLoading = true),
                animeTitle = "Провожающая в последний путь Фрирен",
            ),
        ).asSequence()
}