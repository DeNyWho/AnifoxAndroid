package club.anifox.android.domain.model.query.anime

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum

data class QueryParams(
    val genre: String?,
    val searchQuery: String?,
    val filter: FilterEnum?,
    val status: AnimeStatus?,
    val minimalAge: Int?,
    val season: AnimeSeason?,
    val year: Int?,
    val type: AnimeType?
)