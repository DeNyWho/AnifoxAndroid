package club.anifox.android.feature.detail.components.title.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

internal data class TitleInformationComponentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class TitleInformationComponentProvider:
    PreviewParameterProvider<TitleInformationComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<TitleInformationComponentPreviewParam>
        get() = listOf(
            TitleInformationComponentPreviewParam(
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
            )
        ).asSequence()
}