package club.anifox.android.feature.detail.components.title.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

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
                detailAnime = StateWrapper(data = GlobalParams.Data, isLoading = false),
            )
        ).asSequence()
}