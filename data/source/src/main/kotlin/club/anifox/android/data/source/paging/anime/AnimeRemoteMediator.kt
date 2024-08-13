package club.anifox.android.data.source.paging.anime

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.search.AnimeSearchDao
import club.anifox.android.data.local.cache.model.anime.search.AnimeSearchEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.toEntityCacheSearchLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.common.Resource

@OptIn(ExperimentalPagingApi::class)
class AnimeRemoteMediator(
    private val animeService: AnimeService,
    private val animeSearchDao: AnimeSearchDao,
    private var status: AnimeStatus?,
    private var genres: List<String>?,
    private var searchQuery: String?,
    private var season: AnimeSeason?,
    private var ratingMpa: String?,
    private var minimalAge: String?,
    private var type: AnimeType?,
    private var year: Int?,
    private var studio: String?,
    private var translation: List<Int>?,
    private var filter: FilterEnum?,
) : RemoteMediator<Int, AnimeSearchEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, year, studio, translation, filter)

    data class Params(
        val status: AnimeStatus?,
        val genres: List<String>?,
        val searchQuery: String?,
        val season: AnimeSeason?,
        val ratingMpa: String?,
        val minimalAge: String?,
        val type: AnimeType?,
        val year: Int?,
        val studio: String?,
        val translation: List<Int>?,
        val filter: FilterEnum?
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeSearchEntity>
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
                status = status,
                genres = genres,
                searchQuery = searchQuery,
                season = season,
                ratingMpa = ratingMpa,
                minimalAge = minimalAge,
                type = type,
                year = year,
                studio = studio,
                translation = translation,
                filter = filter,
            )

            when(response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        animeSearchDao.clearAll()
                        lastLoadedPage = -1
                    }
                    val animeEntities = response.data.map { it.toEntityCacheSearchLight() }
                    animeSearchDao.insertAll(animeEntities)
                    lastLoadedPage = loadKey
                    MediatorResult.Success(endOfPaginationReached = animeEntities.isEmpty())
                }
                is Resource.Error -> MediatorResult.Error(Exception("Failed to load: ${response.error}"))
                is Resource.Loading -> MediatorResult.Error(Exception("Unexpected loading state"))
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}