package club.anifox.android.data.source.paging.anime.genres

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.genres.AnimeCacheGenresDao
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.toEntityCacheGenresLight
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeGenresRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheGenresDao: AnimeCacheGenresDao,
    private var genre: String,
    private var minimalAge: Int?,
    private var filter: FilterEnum?,
) : RemoteMediator<Int, AnimeCacheGenresEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(genre, minimalAge, filter)

    private data class Params(
        val genre: String,
        val minimalAge: Int?,
        val filter: FilterEnum?,
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheGenresEntity>
    ): MediatorResult {
        val newParams = Params(genre, minimalAge, filter)
        if (newParams != currentParams) {
            currentParams = newParams
            lastLoadedPage = -1 // Сброс страницы при изменении параметров
        }

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnime(
                page = loadKey,
                limit = state.config.pageSize,
                genres = listOf(currentParams.genre),
                minimalAge = currentParams.minimalAge,
                filter = currentParams.filter,
            )

            when(response) {
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