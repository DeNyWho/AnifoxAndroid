package club.anifox.android.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.domain.usecase.anime.paging.anime.schedule.AnimeSchedulePagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScheduleViewModel @Inject constructor(
    private val animeSchedulePagingUseCase: AnimeSchedulePagingUseCase,
) : ViewModel() {

    // Cache for storing PagingData flows
    private val scheduleCache = mutableMapOf<WeekDay, Flow<PagingData<AnimeLight>>>()

    // Get schedule data with caching
    fun getScheduleForDay(dayOfWeek: WeekDay): Flow<PagingData<AnimeLight>> {
        return scheduleCache.getOrPut(dayOfWeek) {
            animeSchedulePagingUseCase(dayOfWeek = dayOfWeek)
        }
    }

    // Prefetch data for a specific day
    fun prefetchScheduleForDay(dayOfWeek: WeekDay) {
        viewModelScope.launch {
            if (!scheduleCache.containsKey(dayOfWeek)) {
                scheduleCache[dayOfWeek] = animeSchedulePagingUseCase(dayOfWeek = dayOfWeek)
            }
        }
    }
}