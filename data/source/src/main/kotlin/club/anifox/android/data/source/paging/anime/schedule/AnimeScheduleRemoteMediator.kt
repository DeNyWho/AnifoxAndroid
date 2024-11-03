package club.anifox.android.data.source.paging.anime.schedule

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import club.anifox.android.data.local.cache.dao.anime.schedule.AnimeCacheScheduleDao
import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity
import club.anifox.android.data.network.service.AnimeService
import club.anifox.android.data.source.mapper.anime.toEntityCacheScheduleLight
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.domain.model.common.request.Resource

@OptIn(ExperimentalPagingApi::class)
internal class AnimeScheduleRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheScheduleDao: AnimeCacheScheduleDao,
    private var dayOfWeek: WeekDay
) : RemoteMediator<Int, AnimeCacheScheduleEntity>() {

    private var lastLoadedPage = -1
    private var currentDay: WeekDay = dayOfWeek

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheScheduleEntity>
    ): MediatorResult {
        if (currentDay != dayOfWeek) {
            currentDay = dayOfWeek
            lastLoadedPage = -1
        }

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> lastLoadedPage + 1
            }

            val response = animeService.getAnimeSchedule(
                page = loadKey,
                limit = state.config.pageSize,
                dayOfWeek = currentDay.name.lowercase()
            )

            when (response) {
                is Resource.Success -> {
                    if (loadType == LoadType.REFRESH) {
                        animeCacheScheduleDao.deleteSchedulesForDay(currentDay.name)
                        lastLoadedPage = -1
                    }

                    val animeEntities = response.data.getListByDay(currentDay).map { it.toEntityCacheScheduleLight(currentDay)}

                    animeCacheScheduleDao.insertAnimeSchedules(animeEntities)
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