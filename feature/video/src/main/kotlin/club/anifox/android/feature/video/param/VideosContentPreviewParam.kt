package club.anifox.android.feature.video.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper

internal data class VideosContentPreviewParam(
    val modifier: Modifier = Modifier,
    val trailerVideoState: StateListWrapper<AnimeVideosLight>,
    val openingVideoState: StateListWrapper<AnimeVideosLight>,
    val endingVideoState: StateListWrapper<AnimeVideosLight>,
    val otherVideoState: StateListWrapper<AnimeVideosLight>,
    val onVideoClick: (String) -> Unit = { },
    val onBackPressed: () -> Boolean = { false },
    val animeTitle: String? = "",
)

internal class VideosContentProvider:
    PreviewParameterProvider<VideosContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<VideosContentPreviewParam>
        get() = listOf(
            VideosContentPreviewParam(
                modifier = Modifier,
                trailerVideoState = StateListWrapper(isLoading = true),
                openingVideoState = StateListWrapper(isLoading = true),
                endingVideoState = StateListWrapper(isLoading = true),
                otherVideoState = StateListWrapper(isLoading = true),
            ),
            VideosContentPreviewParam(
                modifier = Modifier,
                trailerVideoState = StateListWrapper(isLoading = false),
                openingVideoState = StateListWrapper(isLoading = false),
                endingVideoState = StateListWrapper(isLoading = false),
                otherVideoState = StateListWrapper(isLoading = false),
            ),
        ).asSequence()
}