package su.anifox.domain.model.anime.filter

import su.anifox.domain.model.anime.enum.AnimeOrder
import su.anifox.domain.model.anime.enum.AnimeSeason
import su.anifox.domain.model.anime.enum.AnimeSort
import su.anifox.domain.model.anime.enum.AnimeStatus
import su.anifox.domain.model.anime.enum.AnimeType
import su.anifox.domain.model.anime.enum.RatingMpa

data class AnimeFilters(
    val search: String? = null,
    val genres: List<String>? = null,
    val studios: List<String>? = null,
    val years: List<Int>? = null,
    val season: AnimeSeason? = null,
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val ratingMpa: RatingMpa? = null,
    val minimalAge: Int? = null,
    val translations: List<Int>? = null,
    val order: AnimeOrder? = null,
    val sort: AnimeSort? = null,
    val page: Int = 0,
    val limit: Int = 30,
)