package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

internal data class DetailUIPreviewParam(
    val modifier: Modifier = Modifier,
    val url: String = "",
    val detailAnime: StateWrapper<AnimeDetail>,
    val screenshotsAnime: StateListWrapper<String>,
    val videosAnime: StateListWrapper<AnimeVideosLight>,
    val relationAnime: StateListWrapper<AnimeRelatedLight>,
    val similarAnime: StateListWrapper<AnimeLight>,
    val charactersAnime: StateListWrapper<AnimeCharactersLight>,
    val selectedFavouriteState: AnimeFavouriteStatus = AnimeFavouriteStatus.WATCHING,
    val onBackPressed: () -> Boolean = { true },
    val onAnimeClick: (String) -> Unit = { },
    val onMoreScreenshotClick: (String) -> Unit = { },
    val onMoreVideoClick: (String) -> Unit = { },
)

internal class DetailUIProvider:
    PreviewParameterProvider<DetailUIPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<DetailUIPreviewParam>
        get() = listOf(
            DetailUIPreviewParam(
                modifier = Modifier,
                detailAnime = StateWrapper.loading(),
                relationAnime = StateListWrapper.loading(),
                screenshotsAnime = StateListWrapper.loading(),
                videosAnime = StateListWrapper.loading(),
                similarAnime = StateListWrapper.loading(),
                charactersAnime = StateListWrapper.loading()
            ),
            DetailUIPreviewParam(
                modifier = Modifier,
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
                relationAnime = StateListWrapper(data = GlobalParams.DataSetRelationLight, isLoading = false),
                screenshotsAnime = StateListWrapper(isLoading = false),
                videosAnime = StateListWrapper(isLoading = false),
                similarAnime = StateListWrapper(data = GlobalParams.DataSetAnimeLight, isLoading = false),
                charactersAnime = StateListWrapper(data = GlobalParams.DataSetCharactersLight, isLoading = false)
            )
        ).asSequence()
}