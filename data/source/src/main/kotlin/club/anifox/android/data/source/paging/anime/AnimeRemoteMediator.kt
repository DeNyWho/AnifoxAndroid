package club.anifox.android.data.source.paging.anime

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.dao.anime.AnimeDao
import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.toEntity
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeRemoteMediator(
    private val animeService: AnimeService,
    private val animeDao: AnimeDao,
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
) : RemoteMediator<Int, AnimeEntity>() {

    private var lastLoadedPage = -1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnime(
                page = loadKey,
                limit = state.config.pageSize,
                genres = genres,
                searchQuery = searchQuery,
                filter = filter,
                status = status,
                minimalAge = minimalAge,
                season = season,
                year = year,
                type = type,
                studio = studio,
                translation = translation,
                ratingMpa = ratingMpa,
            )

            when (response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        lastLoadedPage = -1
                    }
                    val animeEntities = response.data.map { it.toEntity() }
                    animeDao.insertOrUpdate(animeEntities)
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