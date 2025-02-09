package club.anifox.android.feature.home.components.genre.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper

internal data class GenreComponentPreviewParam(
    val genresAnime: StateListWrapper<AnimeGenre>,
    val headerTitle: String = "Жанры",
    val onItemClick: (String) -> Unit = { },
)

internal class GenreComponentProvider:
    PreviewParameterProvider<GenreComponentPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GenreComponentPreviewParam>
        get() = listOf(
            GenreComponentPreviewParam(
                genresAnime = StateListWrapper(data = GlobalParams.Genres, isLoading = false),
            )
        ).asSequence()
}