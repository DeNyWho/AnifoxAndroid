package club.anifox.android.feature.catalog.model.state

import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation

internal data class CatalogUiState(
    val selectedGenres: List<AnimeGenre>? = null,
    val selectedStatus: AnimeStatus? = null,
    val selectedType: AnimeType? = null,
    val selectedYears: List<Int>? = null,
    val selectedSeason: AnimeSeason? = null,
    val selectedStudios: List<AnimeStudio>? = null,
    val selectedTranslation: AnimeTranslation? = null,
    val selectedMinimalAge: Int? = null,
    val selectedOrder: AnimeOrder? = null,
    val selectedSort: AnimeSort? = null,
    val isInitialized: Boolean = false,
)