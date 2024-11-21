package club.anifox.android.feature.detail.components.genres.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper

internal data class GenresContentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class GenresContentProvider:
    PreviewParameterProvider<GenresContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GenresContentPreviewParam>
        get() = listOf(
            GenresContentPreviewParam(
                detailAnime = StateWrapper(data = GlobalParams.Data, isLoading = false),
            )
        ).asSequence()
}