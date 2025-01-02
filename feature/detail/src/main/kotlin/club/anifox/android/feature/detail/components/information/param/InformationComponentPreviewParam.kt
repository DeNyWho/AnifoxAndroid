package club.anifox.android.feature.detail.components.information.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

internal data class InformationComponentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class InformationComponentProvider:
    PreviewParameterProvider<InformationComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<InformationComponentPreviewParam>
        get() = listOf(
            InformationComponentPreviewParam(
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
            )
        ).asSequence()
}