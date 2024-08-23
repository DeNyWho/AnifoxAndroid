package club.anifox.android.feature.home.composable.content.genre.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.genre.AnimeGenre

internal data class GenreContentItemPreviewParam(
    val genreAnime: AnimeGenre,
)

internal class GenreContentItemProvider:
    PreviewParameterProvider<GenreContentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<GenreContentItemPreviewParam>
        get() = listOf(
            GenreContentItemPreviewParam(
                genreAnime = AnimeGenre(name = "Приключения"),
            )
        ).asSequence()
}