package club.anifox.android.feature.detail.param

import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.commonui.component.card.CardAnimePortraitDefaults
import club.anifox.android.commonui.component.slider.content.SliderContentDefaults
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper

data class DetailContentPreviewParam(
    val modifier: Modifier = Modifier,
    val detailAnime: StateWrapper<AnimeDetail>,
    val onBackPressed: () -> Boolean = { true },
)

private val Data = AnimeDetail(
    title = "Провожающая в последний путь Фрирен",
    url = "provozhaiushchaia-v-poslednii-put-friren",
)

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
            )
        ).asSequence()
}


