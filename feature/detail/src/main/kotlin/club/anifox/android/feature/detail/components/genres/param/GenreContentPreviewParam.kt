package club.anifox.android.feature.detail.components.genres.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.param.Data

internal data class GenreContentPreviewParam(
    val detailAnime: StateWrapper<AnimeDetail>,
)

internal class GenreContentProvider:
    PreviewParameterProvider<GenreContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GenreContentPreviewParam>
        get() = listOf(
            GenreContentPreviewParam(
                detailAnime = StateWrapper(data = Data, isLoading = false),
            )
        ).asSequence()
}