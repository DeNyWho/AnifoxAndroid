package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

internal data class DetailContentPreviewParam(
    val modifier: Modifier = Modifier,
    val detailAnime: StateWrapper<AnimeDetail>,
    val screenshotsAnime: StateListWrapper<String>,
    val relationAnime: StateListWrapper<AnimeRelatedLight>,
    val similarAnime: StateListWrapper<AnimeLight>,
    val onBackPressed: () -> Boolean = { true },
    val onAnimeClick: (String) -> Unit,
    val onScreenshotClick: (String) -> Unit,
)

internal val Data = AnimeDetail(
    title = "Провожающая в последний путь Фрирен",
    url = "provozhaiushchaia-v-poslednii-put-friren",
    titleEnglish = listOf("Frieren: Beyond Journey's End"),
)

private val DataSetAnimeLight = List(10) {
    AnimeLight(
        title = "Провожающая в последний путь Фрирен",
        image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        url = "provozhaiushchaia-v-poslednii-put-friren$it",
    )
}

private val DataSetRelationLight = List(10) {
    AnimeRelatedLight(
        anime = AnimeLight(
            title = "Провожающая в последний путь Фрирен",
            image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
            url = "provozhaiushchaia-v-poslednii-put-friren$it"
        ),
        type = "type",
    )
}

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
                detailAnime = StateWrapper(data = Data, isLoading = false),
                relationAnime = StateListWrapper(data = DataSetRelationLight, isLoading = false),
                screenshotsAnime = StateListWrapper(isLoading = false),
                similarAnime = StateListWrapper(data = DataSetAnimeLight, isLoading = false),
                onAnimeClick = { },
                onScreenshotClick = { },
            )
        ).asSequence()
}