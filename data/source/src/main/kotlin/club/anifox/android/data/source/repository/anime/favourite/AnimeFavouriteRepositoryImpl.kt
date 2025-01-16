package club.anifox.android.data.source.repository.anime.favourite

import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.dao.anime.favourite.AnimeFavouriteDao
import club.anifox.android.data.local.model.anime.favourite.AnimeFavouriteEntity
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class AnimeFavouriteRepositoryImpl(
    private val favouriteDao: AnimeFavouriteDao,
    private val animeDao: AnimeDao,
) : AnimeFavouriteRepository {

    override fun getHistoryAnime(): Flow<List<AnimeLightFavourite>> =
        favouriteDao.getHistoryAnime()

    override fun getFavouriteAnime(): Flow<List<AnimeLightFavourite>> =
        favouriteDao.getFavouriteAnime()

    override fun getAnimeByStatus(status: AnimeFavouriteStatus): Flow<List<AnimeLightFavourite>> =
        favouriteDao.getAnimeByStatus(status)

    override fun getAllAnimeLightFavourites(): Flow<List<AnimeLightFavourite>> =
        favouriteDao.getAllAnimeLightFavourites()

    override suspend fun isAnimeInFavourite(url: String): Boolean =
        favouriteDao.isAnimeInFavourite(url)

    override suspend fun getAnimeStatus(url: String): AnimeFavouriteStatus? =
        favouriteDao.getAnimeStatus(url)

    override suspend fun updateAnimeStatus(url: String, status: AnimeFavouriteStatus?) {
        val favourite = favouriteDao.getStatusById(url) ?: AnimeFavouriteEntity(
            animeUrl = url,
            addedAt = LocalDateTime.now()
        )
        favouriteDao.insertStatusIfAnimeExists(
            animeUrl = url,
            status = favourite.copy(
                watchStatus = status,
                lastUpdatedAt = LocalDateTime.now()
            ),
            animeDao = animeDao,
        )
    }

    override suspend fun updateAnimeFavourite(url: String, isFavourite: Boolean) {
        val favourite = favouriteDao.getStatusById(url) ?: AnimeFavouriteEntity(
            animeUrl = url,
            addedAt = LocalDateTime.now()
        )
        favouriteDao.insertStatusIfAnimeExists(
            animeUrl = url,
            status = favourite.copy(
                isFavourite = isFavourite,
                lastUpdatedAt = LocalDateTime.now()
            ),
            animeDao = animeDao,
        )
    }

    override suspend fun updateAnimeHistory(url: String, isInHistory: Boolean) {
        val favourite = favouriteDao.getStatusById(url) ?: AnimeFavouriteEntity(
            animeUrl = url,
            addedAt = LocalDateTime.now()
        )
        favouriteDao.insertStatusIfAnimeExists(
            animeUrl = url,
            status = favourite.copy(
                isInHistory = isInHistory,
                lastUpdatedAt = LocalDateTime.now()
            ),
            animeDao = animeDao,
        )
    }
}