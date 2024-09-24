package club.anifox.android.feature.catalog.data

import club.anifox.android.domain.model.anime.enum.FilterEnum

internal data class CatalogState(
    val genre: String = "",
    val minimalAge: Int? = null,
    val filter: FilterEnum = FilterEnum.ShikimoriRating,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)