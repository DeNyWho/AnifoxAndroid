package club.anifox.android.feature.detail.components.information.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.param.Data

internal data class InformationContentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class InformationContentProvider:
    PreviewParameterProvider<InformationContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<InformationContentPreviewParam>
        get() = listOf(
            InformationContentPreviewParam(
                detailAnime = StateWrapper(data = Data, isLoading = false),
            )
        ).asSequence()
}