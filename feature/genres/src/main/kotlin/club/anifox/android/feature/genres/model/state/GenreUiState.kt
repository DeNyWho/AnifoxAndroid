package club.anifox.android.feature.genres.model.state

import club.anifox.android.domain.model.anime.genre.AnimeGenre

internal data class GenreUiState(
    val genres: List<AnimeGenre> = emptyList(),
    val minimalAge: Int? = null,
    val isInitialized: Boolean = false,
    val isGenresLoaded: Boolean = false,
    val selectedGenre: AnimeGenre = AnimeGenre()
)