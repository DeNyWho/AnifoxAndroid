package club.anifox.android.feature.episodes.model.state

import club.anifox.android.domain.model.anime.enum.AnimeSort

internal data class EpisodesUiState(
    val url: String = "",
    val translationId: Int = 0,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
    val selectedSort: AnimeSort = AnimeSort.Desc,
    val searchQuery: String = "",
)