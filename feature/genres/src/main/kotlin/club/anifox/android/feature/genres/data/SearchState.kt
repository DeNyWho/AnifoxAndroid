package club.anifox.android.feature.genres.data

import club.anifox.android.domain.model.anime.enum.FilterEnum

internal data class SearchState(
    val genre: String = "",
    val minimalAge: Int? = null,
    val filter: FilterEnum = FilterEnum.ShikimoriRating,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)