package club.anifox.android.feature.schedule.model.state

import club.anifox.android.domain.model.common.enum.WeekDay

internal data class ScheduleUiState(
    val isLoading: Boolean = false,
    val loadingMap: Map<WeekDay, Boolean> = emptyMap()
)