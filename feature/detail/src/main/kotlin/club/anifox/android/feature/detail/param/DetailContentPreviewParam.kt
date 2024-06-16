package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

data class DetailContentPreviewParam(
    val modifier: Modifier = Modifier,
    val detailAnime: StateWrapper<AnimeDetail>,
    val similarAnime: StateListWrapper<AnimeLight>,
    val onBackPressed: () -> Boolean = { true },
    val onAnimeClick: (String) -> Unit,
)

internal val Data = AnimeDetail(
    title = "Провожающая в последний путь Фрирен",
    url = "provozhaiushchaia-v-poslednii-put-friren",
    titleEnglish = listOf("Frieren: Beyond Journey's End"),
)

private val DataSet = List(10) {
    AnimeLight(
        title = "Провожающая в последний путь Фрирен",
        image = "https://cdn.anifox.club/images/anime/large/provozhaiushchaia-v-poslednii-put-friren/08f43e5054966f85ed4bcdbe7dc77b7b.png",
        url = "provozhaiushchaia-v-poslednii-put-friren$it"
    )
}

class DetailContentProvider:
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
                similarAnime = StateListWrapper(data = DataSet, isLoading = false),
                onAnimeClick = { },
            )
        ).asSequence()
}