package club.anifox.android.domain.repository.anime

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import kotlinx.coroutines.flow.Flow

interface AnimeFavouriteRepository {
    suspend fun updateAnimeStatus(url: String, status: AnimeFavouriteStatus?)
    suspend fun updateAnimeFavourite(url: String, isFavourite: Boolean)
    suspend fun updateAnimeHistory(url: String, isInHistory: Boolean)
    suspend fun isAnimeInFavourite(url: String): Boolean
    suspend fun getAnimeStatus(url: String): AnimeFavouriteStatus?
    fun getHistoryAnime(): Flow<PagingData<AnimeLightFavourite>>
    fun getFavouriteAnime(): Flow<PagingData<AnimeLightFavourite>>
    fun getAnimeByStatus(status: AnimeFavouriteStatus): Flow<PagingData<AnimeLightFavourite>>
}