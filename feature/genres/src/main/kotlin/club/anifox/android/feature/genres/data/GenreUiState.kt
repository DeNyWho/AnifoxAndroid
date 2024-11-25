package club.anifox.android.feature.genres.data

import club.anifox.android.domain.model.anime.genre.AnimeGenre

internal data class GenreUiState(
    val genres: List<AnimeGenre> = emptyList(),
    val minimalAge: Int? = null,
    val isLoading: Boolean = true,
    val isReadyToLoad: Boolean = false,
    val isGenresLoaded: Boolean = false,
    val isContentLoading: Boolean = true,
    val selectedGenre: AnimeGenre = AnimeGenre()
)