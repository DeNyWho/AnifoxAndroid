package club.anifox.android.feature.catalog.data

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation

internal data class CatalogState(
    val genres: List<AnimeGenre>? = null,
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val year: Int? = null,
    val season: AnimeSeason? = null,
    val studios: List<AnimeStudio>? = null,
    val translation: AnimeTranslation? = null,
    val minimalAge: Int? = null,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)