package club.anifox.android.feature.catalog.data

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.anime.translations.AnimeTranslation

internal data class CatalogState(
    val genres: List<String>? = null,
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val year: Int? = null,
    val season: AnimeSeason? = null,
    val studio: List<String>? = null,
    val translation: AnimeTranslation? = null,
    val minimalAge: Int? = null,
    val filter: FilterEnum? = null,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)