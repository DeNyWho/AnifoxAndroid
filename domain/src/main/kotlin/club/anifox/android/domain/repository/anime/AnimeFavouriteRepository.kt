package club.anifox.android.domain.repository.anime

import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import kotlinx.coroutines.flow.Flow

interface AnimeFavouriteRepository {
    fun getHistoryAnime(): Flow<List<AnimeLightFavourite>>
    fun getFavouriteAnime(): Flow<List<AnimeLightFavourite>>
    fun getAnimeByStatus(status: AnimeFavouriteStatus): Flow<List<AnimeLightFavourite>>
    fun getAllAnimeLightFavourites(): Flow<List<AnimeLightFavourite>>
    suspend fun updateAnimeStatus(url: String, status: AnimeFavouriteStatus?)
    suspend fun updateAnimeFavourite(url: String, isFavourite: Boolean)
    suspend fun updateAnimeHistory(url: String, isInHistory: Boolean)
}