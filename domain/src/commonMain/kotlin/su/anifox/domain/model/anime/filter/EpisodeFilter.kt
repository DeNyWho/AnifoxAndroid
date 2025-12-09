package su.anifox.domain.model.anime.filter

import su.anifox.domain.model.anime.enum.AnimeSort
import su.anifox.domain.model.anime.enum.episode.EpisodeOrder

data class EpisodeFilters(
    val animeUrl: String,
    val page: Int = 0,
    val limit: Int = 50,
    val order: EpisodeOrder = EpisodeOrder.NUMBER,
    val sort: AnimeSort = AnimeSort.ASC,
)