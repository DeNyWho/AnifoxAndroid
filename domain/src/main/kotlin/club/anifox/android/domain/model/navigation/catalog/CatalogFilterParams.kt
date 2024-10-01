package club.anifox.android.domain.model.navigation.catalog

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum

data class CatalogFilterParams(
    val genres: List<String>? = null,
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val year: Int? = null,
    val season: AnimeSeason? = null,
    val studio: List<String>? = null,
    val filter: FilterEnum? = null,
)