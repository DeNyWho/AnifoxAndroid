package club.anifox.android.feature.home.composable.content.genre.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper

internal data class GenreContentPreviewParam(
    val genresAnime: StateListWrapper<AnimeGenre>,
    val headerTitle: String = "Жанры",
)

internal class GenreContentProvider:
    PreviewParameterProvider<GenreContentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GenreContentPreviewParam>
        get() = listOf(
            GenreContentPreviewParam(
                genresAnime = StateListWrapper(data = GlobalParams.Genres, isLoading = false),
            )
        ).asSequence()
}