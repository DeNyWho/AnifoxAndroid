package su.anifox.domain.model.anime.enum

sealed interface AnimeListType {
    data object History : AnimeListType
    data object Favourite : AnimeListType
    data class Status(val status: AnimeFavouriteStatus) : AnimeListType
}