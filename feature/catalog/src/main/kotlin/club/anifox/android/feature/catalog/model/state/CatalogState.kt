package club.anifox.android.feature.catalog.model.state

import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation

internal data class CatalogState(
    val genres: List<AnimeGenre>? = null,
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val years: List<Int>? = null,
    val season: AnimeSeason? = null,
    val studios: List<AnimeStudio>? = null,
    val translation: AnimeTranslation? = null,
    val minimalAge: Int? = null,
    val order: AnimeOrder? = null,
    val sort: AnimeSort? = null,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)