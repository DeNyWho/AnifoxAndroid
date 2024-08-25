package club.anifox.android.feature.home.composable.content.genre.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.genre.AnimeGenre

internal data class CardGenreContentItemPreviewParam(
    val genreAnime: AnimeGenre,
)

internal class CardGenreContentItemProvider:
    PreviewParameterProvider<CardGenreContentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CardGenreContentItemPreviewParam>
        get() = listOf(
            CardGenreContentItemPreviewParam(
                genreAnime = AnimeGenre(name = "Приключения"),
            )
        ).asSequence()
}