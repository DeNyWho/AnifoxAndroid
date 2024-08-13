package club.anifox.android.feature.search.data

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation

internal data class SearchState(
    val query: String = "",
    val status: AnimeStatus? = null,
    val type: AnimeType? = null,
    val year: Int? = null,
    val season: AnimeSeason? = null,
    val studio: AnimeStudio? = null,
    val translation: AnimeTranslation? = null,
    val isLoading: Boolean = false,
)