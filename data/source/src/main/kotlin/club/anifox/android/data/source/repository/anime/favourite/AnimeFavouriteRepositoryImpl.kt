package club.anifox.android.data.source.repository.anime.favourite

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.dao.anime.favourite.AnimeFavouriteDao
import club.anifox.android.data.local.model.anime.favourite.AnimeFavouriteEntity
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.repository.anime.AnimeFavouriteRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

class AnimeFavouriteRepositoryImpl(
    private val animeFavouriteDao: AnimeFavouriteDao,
    private val animeDao: AnimeDao,
) : AnimeFavouriteRepository {

    override fun getHistoryAnime(): Flow<PagingData<AnimeLightFavourite>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { animeFavouriteDao.getHistoryAnimePaging() }
        ).flow
    }

    override fun getFavouriteAnime(): Flow<PagingData<AnimeLightFavourite>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { animeFavouriteDao.getFavouriteAnimePaging() }
        ).flow
    }

    override fun getAnimeByStatus(status: AnimeFavouriteStatus): Flow<PagingData<AnimeLightFavourite>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { animeFavouriteDao.getAnimeByStatusPaging(status) }
        ).flow
    }

    override suspend fun isAnimeInFavourite(url: String): Boolean =
        animeFavouriteDao.isAnimeInFavourite(url)

    override suspend fun getAnimeStatus(url: String): AnimeFavouriteStatus? =
        animeFavouriteDao.getAnimeStatus(url)

    override suspend fun updateAnimeStatus(url: String, status: AnimeFavouriteStatus?) {
        val favourite = animeFavouriteDao.getStatusById(url) ?: AnimeFavouriteEntity(
            animeUrl = url,
            addedAt = LocalDateTime.now()
        )
        animeFavouriteDao.insertStatusIfAnimeExists(
            animeUrl = url,
            status = favourite.copy(
                watchStatus = status,
                lastUpdatedAt = LocalDateTime.now()
            ),
            animeDao = animeDao,
        )
    }

    override suspend fun updateAnimeFavourite(url: String, isFavourite: Boolean) {
        val favourite = animeFavouriteDao.getStatusById(url) ?: AnimeFavouriteEntity(
            animeUrl = url,
            addedAt = LocalDateTime.now()
        )
        animeFavouriteDao.insertStatusIfAnimeExists(
            animeUrl = url,
            status = favourite.copy(
                isFavourite = isFavourite,
                lastUpdatedAt = LocalDateTime.now()
            ),
            animeDao = animeDao,
        )
    }

    override suspend fun updateAnimeHistory(url: String, isInHistory: Boolean) {
        val favourite = animeFavouriteDao.getStatusById(url) ?: AnimeFavouriteEntity(
            animeUrl = url,
            addedAt = LocalDateTime.now()
        )
        animeFavouriteDao.insertStatusIfAnimeExists(
            animeUrl = url,
            status = favourite.copy(
                isInHistory = isInHistory,
                lastUpdatedAt = LocalDateTime.now()
            ),
            animeDao = animeDao,
        )
    }
}