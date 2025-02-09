package club.anifox.android.feature.home.components.genre.item.param

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import club.anifox.android.domain.model.anime.genre.AnimeGenre

internal data class CardGenreComponentItemPreviewParam(
    val genreAnime: AnimeGenre,
    val onItemClick: (String) -> Unit = { },
)

internal class CardGenreComponentItemProvider:
    PreviewParameterProvider<CardGenreComponentItemPreviewParam> {
    override val count: Int
        get() = super.count
    override val values: Sequence<CardGenreComponentItemPreviewParam>
        get() = listOf(
            CardGenreComponentItemPreviewParam(
                genreAnime = AnimeGenre(name = "Приключения"),
            )
        ).asSequence()
}