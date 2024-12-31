package club.anifox.android.feature.detail.components.information.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

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
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
            )
        ).asSequence()
}