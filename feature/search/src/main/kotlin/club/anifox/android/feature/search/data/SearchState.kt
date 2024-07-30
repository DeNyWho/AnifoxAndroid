package club.anifox.android.feature.search.data

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType

internal data class SearchState(
    val query: String = "",
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val year: Int? = null,
    val season: AnimeSeason? = null,
    val isLoading: Boolean = false,
)