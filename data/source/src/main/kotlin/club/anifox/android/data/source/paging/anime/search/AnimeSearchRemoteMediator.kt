package club.anifox.android.data.source.paging.anime.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.anime.toEntityCacheSearchLight
import club.anifox.android.domain.model.common.request.Resource
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalPagingApi::class)
internal class AnimeSearchRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheSearchDao: AnimeCacheSearchDao,
    private var searchQuery: String?,
) : RemoteMediator<Int, AnimeCacheSearchEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(searchQuery)

    private data class Params(
        val searchQuery: String?,
    )

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheSearchEntity>
    ): MediatorResult {
        val newParams = Params(searchQuery)
        if (newParams != currentParams) {
            currentParams = newParams
            lastLoadedPage = -1
            animeCacheSearchDao.clearAll()
        }

        return try {
            if (newParams != currentParams) {
                currentParams = newParams
                return load(LoadType.REFRESH, state)
            }

            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    animeCacheSearchDao.clearAll()
                    lastLoadedPage = -1
                    0
                }

                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnime(
                page = loadKey,
                limit = state.config.pageSize,
                searchQuery = currentParams.searchQuery,
            )

            when (response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        animeCacheSearchDao.clearAll()
                        lastLoadedPage = -1
                    }
                    val animeEntities = response.data.map { it.toEntityCacheSearchLight() }
                    animeCacheSearchDao.insertAll(animeEntities)
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
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}