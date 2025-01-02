package club.anifox.android.feature.detail.components.genres.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

internal data class GenresComponentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class GenresComponentProvider:
    PreviewParameterProvider<GenresComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GenresComponentPreviewParam>
        get() = listOf(
            GenresComponentPreviewParam(
                detailAnime = StateWrapper(data = GlobalParams.DataAnimeDetail, isLoading = false),
            )
        ).asSequence()
}