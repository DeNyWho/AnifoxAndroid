package club.anifox.android.feature.schedule.state

import club.anifox.android.domain.model.common.enum.WeekDay

internal data class ScheduleState(
    val isLoading: Boolean = false,
    val loadingMap: Map<WeekDay, Boolean> = emptyMap()
)