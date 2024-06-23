package club.anifox.android.feature.detail.components.title.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.param.Data

internal data class TitleInformationContentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class TitleInformationContentProvider:
    PreviewParameterProvider<TitleInformationContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TitleInformationContentPreviewParam>
        get() = listOf(
//            DetailContentPreviewParam(
//                modifier = Modifier,
//                detailAnime = StateWrapper.loading(),
//            ),
            TitleInformationContentPreviewParam(
                detailAnime = StateWrapper(data = Data, isLoading = false),
            )
        ).asSequence()
}