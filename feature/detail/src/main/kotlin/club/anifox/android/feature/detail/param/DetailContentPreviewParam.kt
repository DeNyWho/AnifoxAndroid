package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

internal data class DetailContentPreviewParam(
    val modifier: Modifier = Modifier,
    val url: String = "",
    val detailAnime: StateWrapper<AnimeDetail>,
    val screenshotsAnime: StateListWrapper<String>,
    val videosAnime: StateListWrapper<AnimeVideosLight>,
    val relationAnime: StateListWrapper<AnimeRelatedLight>,
    val similarAnime: StateListWrapper<AnimeLight>,
    val charactersAnime: StateListWrapper<AnimeCharactersLight>,
    val onBackPressed: () -> Boolean = { true },
    val onAnimeClick: (String) -> Unit = { },
    val onMoreScreenshotClick: (String) -> Unit = { },
    val onMoreVideoClick: (String) -> Unit = { },
)

internal class DetailContentProvider:
    PreviewParameterProvider<DetailContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<DetailContentPreviewParam>
        get() = listOf(
            DetailContentPreviewParam(
                modifier = Modifier,
                detailAnime = StateWrapper.loading(),
                relationAnime = StateListWrapper.loading(),
                screenshotsAnime = StateListWrapper.loading(),
                videosAnime = StateListWrapper.loading(),
                similarAnime = StateListWrapper.loading(),
                charactersAnime = StateListWrapper.loading()
            ),
            DetailContentPreviewParam(
                modifier = Modifier,
                detailAnime = StateWrapper(data = GlobalParams.Data, isLoading = false),
                relationAnime = StateListWrapper(data = GlobalParams.DataSetRelationLight, isLoading = false),
                screenshotsAnime = StateListWrapper(isLoading = false),
                videosAnime = StateListWrapper(isLoading = false),
                similarAnime = StateListWrapper(data = GlobalParams.DataSetAnimeLight, isLoading = false),
                charactersAnime = StateListWrapper(data = GlobalParams.DataSetCharactersLight, isLoading = false)
            )
        ).asSequence()
}