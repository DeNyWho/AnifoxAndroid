package club.anifox.android.feature.video.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.video.model.state.VideoUiState

internal data class VideosUIPreviewParam(
    val modifier: Modifier = Modifier,
    val uiState: VideoUiState = VideoUiState(),
    val trailerVideoState: StateListWrapper<AnimeVideosLight>,
    val openingVideoState: StateListWrapper<AnimeVideosLight>,
    val endingVideoState: StateListWrapper<AnimeVideosLight>,
    val otherVideoState: StateListWrapper<AnimeVideosLight>,
    val onVideoClick: (String) -> Unit = { },
    val onBackPressed: () -> Unit = { },
)

internal class VideosUIProvider:
    PreviewParameterProvider<VideosUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<VideosUIPreviewParam>
        get() = listOf(
            VideosUIPreviewParam(
                modifier = Modifier,
                trailerVideoState = StateListWrapper.loading(),
                openingVideoState = StateListWrapper.loading(),
                endingVideoState = StateListWrapper.loading(),
                otherVideoState = StateListWrapper.loading(),
            ),
        ).asSequence()
}