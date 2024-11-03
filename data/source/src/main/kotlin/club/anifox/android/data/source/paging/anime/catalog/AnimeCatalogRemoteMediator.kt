package club.anifox.android.data.source.paging.anime.catalog

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.catalog.AnimeCacheCatalogDao
import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.anime.toEntityCacheCatalogLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
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
    private var years: List<Int>?,
    private var studios: List<String>?,
    private var translation: List<Int>?,
    private var order: AnimeOrder? = null,
    private var sort: AnimeSort? = null,
) : RemoteMediator<Int, AnimeCacheCatalogEntity>() {

    private var lastLoadedPage = -1
    private var currentParams: Params = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, years, studios, translation, order, sort)

    private data class Params(
        val status: AnimeStatus?,
        val genres: List<String>?,
        val searchQuery: String?,
        val season: AnimeSeason?,
        val ratingMpa: String?,
        val minimalAge: Int?,
        val type: AnimeType?,
        val years: List<Int>?,
        val studios: List<String>?,
        val translation: List<Int>?,
        val order: AnimeOrder?,
        val sort: AnimeSort?,
    )

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheCatalogEntity>
    ): MediatorResult {
        val newParams = Params(status, genres, searchQuery, season, ratingMpa, minimalAge, type, years, studios, translation, order, sort)
        if (newParams != currentParams) {
            currentParams = newParams
            lastLoadedPage = -1
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
                years = currentParams.years,
                studios = currentParams.studios,
                translation = currentParams.translation,
                order = currentParams.order,
                sort = currentParams.sort,
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