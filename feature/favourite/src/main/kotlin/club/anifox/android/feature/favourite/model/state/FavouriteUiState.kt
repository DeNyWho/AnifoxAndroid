package club.anifox.android.feature.favourite.model.state

import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.feature.favourite.model.tab.FavouriteTabType

internal data class FavouriteUiState(
    val currentTab: FavouriteTabType = FavouriteTabType.HISTORY,
    val tabs: List<FavouriteTabType> = listOf(
        FavouriteTabType.HISTORY,
        FavouriteTabType.FAVOURITE
    ) + AnimeFavouriteStatus.entries.map { FavouriteTabType.STATUS(it) },
    val items: Map<FavouriteTabType, List<AnimeLightFavourite>> = emptyMap(),
    val error: String? = null,
)