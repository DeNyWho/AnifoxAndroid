package club.anifox.android.feature.favourite.model.tab

import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.model.anime.enum.AnimeListType

sealed class FavouriteTabType {
    data object HISTORY : FavouriteTabType()
    data object FAVOURITE : FavouriteTabType()
    data class STATUS(val status: AnimeFavouriteStatus) : FavouriteTabType()

    fun toAnimeListType(): AnimeListType = when (this) {
        HISTORY -> AnimeListType.History
        FAVOURITE -> AnimeListType.Favourite
        is STATUS -> AnimeListType.Status(status)
    }

    companion object {
        fun getAllStatuses(): List<FavouriteTabType> {
            return AnimeFavouriteStatus.entries
                .map { STATUS(it) }
        }

        fun getAllEntries(): List<FavouriteTabType> {
            return listOf(FavouriteTabType.FAVOURITE) + AnimeFavouriteStatus.entries
                .map { STATUS(it) }
        }
    }
}