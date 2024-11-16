package club.anifox.android.feature.schedule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.enum.WeekDay
import club.anifox.android.domain.usecase.anime.paging.anime.schedule.AnimeSchedulePagingUseCase
import club.anifox.android.feature.schedule.state.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ScheduleViewModel @Inject constructor(
    private val animeSchedulePagingUseCase: AnimeSchedulePagingUseCase,
) : ViewModel() {
    private val _scheduleState = MutableStateFlow(ScheduleState())
    val scheduleState = _scheduleState.asStateFlow()

    private fun updateLoadingState(dayOfWeek: WeekDay, isLoading: Boolean) {
        _scheduleState.update {
            val updatedLoadingMap = it.loadingMap.toMutableMap().apply {
                this[dayOfWeek] = isLoading
            }
            it.copy(
                isLoading = updatedLoadingMap.values.any { loading -> loading },
                loadingMap = updatedLoadingMap
            )
        }
    }

    private val scheduleCache = mutableMapOf<WeekDay, Flow<PagingData<AnimeLight>>>()

    fun getScheduleForDay(dayOfWeek: WeekDay): Flow<PagingData<AnimeLight>> {
        return scheduleCache.getOrPut(dayOfWeek) {
            animeSchedulePagingUseCase(dayOfWeek = dayOfWeek)
        }
    }

    fun prefetchScheduleForDay(dayOfWeek: WeekDay) {
        viewModelScope.launch {
            if (!scheduleCache.containsKey(dayOfWeek)) {
                updateLoadingState(dayOfWeek, true)
                scheduleCache[dayOfWeek] = animeSchedulePagingUseCase(dayOfWeek = dayOfWeek).also {
                    updateLoadingState(dayOfWeek, false)
                }
            }
        }
    }
}