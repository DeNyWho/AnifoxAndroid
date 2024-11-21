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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagingApi::class)
internal class AnimeScheduleRemoteMediator(
    private val animeService: AnimeService,
    private val animeCacheScheduleDao: AnimeCacheScheduleDao,
    private var dayOfWeek: WeekDay
) : RemoteMediator<Int, AnimeCacheScheduleEntity>() {

    private var currentDay: WeekDay = dayOfWeek
    private var lastUpdateTime: Long = 0

    companion object {
        private const val CACHE_TIMEOUT = 5 * 60 * 1000
        private const val PAGE_SIZE = 100
    }

    // Initialize the RemoteMediator, deciding if an initial refresh should occur
    override suspend fun initialize(): InitializeAction {
        val currentTime = System.currentTimeMillis()
        return if (shouldRefresh(currentTime)) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    // Determines if a refresh is needed based on various conditions
    private suspend fun shouldRefresh(currentTime: Long): Boolean {
        // Check if the cache has timed out
        val cacheTimeout = currentTime - lastUpdateTime > CACHE_TIMEOUT

        // Check if there are no cached items for the current day
        val cacheCount = animeCacheScheduleDao.getAnimeCountForDay(dayOfWeek.name)
        val cacheEmpty = cacheCount == 0

        // Check if the day has changed
        val dayChanged = currentDay != dayOfWeek

        return cacheTimeout || cacheEmpty || dayChanged
    }

    // Loads data from the network or cache based on the current load type
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, AnimeCacheScheduleEntity>
    ): MediatorResult {
        try {
            if (currentDay != dayOfWeek) {
                currentDay = dayOfWeek
            }

            // For APPEND and PREPEND, we always return success because we load everything at once
            if (loadType != LoadType.REFRESH) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            // Check if a refresh is needed
            if (!shouldRefresh(System.currentTimeMillis())) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            // Perform a single request with a large limit
            val response = animeService.getAnimeSchedule(
                page = 0,
                limit = PAGE_SIZE
            )

            return when (response) {
                is Resource.Success -> {
                    coroutineScope {
                        launch {
                            // Clear the old data
                            animeCacheScheduleDao.deleteSchedulesForDay(dayOfWeek.name)

                            // Save the new data
                            val animeEntities = response.data.getListByDay(dayOfWeek).map {
                                it.toEntityCacheScheduleLight(dayOfWeek)
                            }

                            if (animeEntities.isNotEmpty()) {
                                animeCacheScheduleDao.insertAnimeSchedules(animeEntities)
                                lastUpdateTime = System.currentTimeMillis() // Update the last update time
                            }
                        }
                    }

                    MediatorResult.Success(endOfPaginationReached = true)
                }
                is Resource.Error -> MediatorResult.Error(Exception("Failed to load: ${response.error}"))
                is Resource.Loading -> MediatorResult.Error(Exception("Unexpected loading state"))
            }
        } catch (e: Exception) {
            return MediatorResult.Error(e) // Return an error if an exception occurs
        }
    }
}