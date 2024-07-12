package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

internal data class DetailContentPreviewParam(
    val modifier: Modifier = Modifier,
    val detailAnime: StateWrapper<AnimeDetail>,
    val screenshotsAnime: StateListWrapper<String>,
    val videosAnime: StateListWrapper<AnimeVideosLight>,
    val relationAnime: StateListWrapper<AnimeRelatedLight>,
    val similarAnime: StateListWrapper<AnimeLight>,
    val onBackPressed: () -> Boolean = { true },
    val onAnimeClick: (String) -> Unit,
    val onScreenshotClick: (String) -> Unit,
)

internal class DetailContentProvider:
    PreviewParameterProvider<DetailContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<DetailContentPreviewParam>
        get() = listOf(
//            DetailContentPreviewParam(
//                modifier = Modifier,
//                detailAnime = StateWrapper.loading(),
//            ),
            DetailContentPreviewParam(
                modifier = Modifier,
                detailAnime = StateWrapper(data = GlobalParams.Data, isLoading = false),
                relationAnime = StateListWrapper(data = GlobalParams.DataSetRelationLight, isLoading = false),
                screenshotsAnime = StateListWrapper(isLoading = false),
                videosAnime = StateListWrapper(isLoading = false),
                similarAnime = StateListWrapper(data = GlobalParams.DataSetAnimeLight, isLoading = false),
                onAnimeClick = { },
                onScreenshotClick = { },
            )
        ).asSequence()
}