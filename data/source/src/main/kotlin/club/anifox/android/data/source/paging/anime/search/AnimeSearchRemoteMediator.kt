package club.anifox.android.data.source.paging.anime.search

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.search.AnimeCacheSearchDao
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.toEntityCacheSearchLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.common.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeSearchRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheSearchDao: AnimeCacheSearchDao,
    private var status: AnimeStatus?,
    private var genres: List<String>?,
    private var searchQuery: String?,
    private var season: AnimeSeason?,
    private var ratingMpa: String?,
    private var minimalAge: Int?,
    private var type: AnimeType?,
    private var year: Int?,
    private var studio: String?,
    private var translation: List<Int>?,
    private var filter: FilterEnum?,
) : RemoteMediator<Int, AnimeCacheSearchEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, year, studio, translation, filter)

    private data class Params(
        val status: AnimeStatus?,
        val genres: List<String>?,
        val searchQuery: String?,
        val season: AnimeSeason?,
        val ratingMpa: String?,
        val minimalAge: Int?,
        val type: AnimeType?,
        val year: Int?,
        val studio: String?,
        val translation: List<Int>?,
        val filter: FilterEnum?,
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheSearchEntity>
    ): MediatorResult {
        val newParams = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, year, studio, translation, filter)
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
                status = currentParams.status,
                genres = currentParams.genres,
                searchQuery = currentParams.searchQuery,
                season = currentParams.season,
                ratingMpa = currentParams.ratingMpa,
                minimalAge = currentParams.minimalAge,
                type = currentParams.type,
                year = currentParams.year,
                studio = currentParams.studio,
                translation = currentParams.translation,
                filter = currentParams.filter,
            )

            when(response) {
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
                    println("ERROR MEDIATOR SEARCH")
                    MediatorResult.Error(Exception("Failed to load: ${response.error}"))
                }
                is Resource.Loading -> {
                    println("HERE LOADING")
                    MediatorResult.Error(Exception("Unexpected loading state"))
                }
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}