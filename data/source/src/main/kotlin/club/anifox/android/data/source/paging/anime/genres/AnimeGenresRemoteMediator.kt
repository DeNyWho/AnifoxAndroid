package club.anifox.android.data.source.paging.anime.genres

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.anime.toEntityCacheGenresLight
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeGenresRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheGenresDao: AnimeCacheGenresDao,
    private var genre: String,
    private var minimalAge: Int?,
) : RemoteMediator<Int, AnimeCacheGenresEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(
        genre = genre,
        minimalAge = minimalAge,
    )

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    private data class Params(
        val genre: String,
        val minimalAge: Int?,
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheGenresEntity>
    ): MediatorResult {
        val newParams = Params(
            genre = genre,
            minimalAge = minimalAge,
        )

        if (newParams != currentParams) {
            currentParams = newParams
            lastLoadedPage = -1
            animeCacheGenresDao.clearAll()
        }

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    animeCacheGenresDao.clearAll()
                    lastLoadedPage = -1
                    0
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnime(
                page = loadKey,
                limit = state.config.pageSize,
                genres = listOf(currentParams.genre),
                minimalAge = currentParams.minimalAge,
            )

            when (response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        animeCacheGenresDao.clearAll()
                        lastLoadedPage = -1
                    }

                    val animeEntities = response.data.map { it.toEntityCacheGenresLight() }
                    animeCacheGenresDao.insertAll(animeEntities)
                    lastLoadedPage = loadKey
                    MediatorResult.Success(endOfPaginationReached = animeEntities.isEmpty())
                }

                is Resource.Error -> {
                    MediatorResult.Error(Exception("Failed to load: ${response.error}"))
                }

                is Resource.Loading -> {
                    MediatorResult.Error(Exception("Unexpected loading state"))
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}