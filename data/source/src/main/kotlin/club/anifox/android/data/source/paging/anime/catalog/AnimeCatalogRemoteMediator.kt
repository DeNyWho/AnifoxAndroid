package club.anifox.android.data.source.paging.anime.catalog

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.toEntityCacheCatalogLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeCatalogRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheCatalogDao: AnimeCacheCatalogDao,
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
) : RemoteMediator<Int, AnimeCacheCatalogEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, year, studio, translation)

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
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheCatalogEntity>
    ): MediatorResult {
        val newParams = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, year, studio, translation)
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
            )

            when(response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        animeCacheCatalogDao.clearAll()
                        lastLoadedPage = -1
                    }
                    val animeEntities = response.data.map { it.toEntityCacheCatalogLight() }
                    animeCacheCatalogDao.insertAll(animeEntities)
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