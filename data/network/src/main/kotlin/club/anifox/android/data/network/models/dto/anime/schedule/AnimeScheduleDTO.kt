package club.anifox.android.data.network.models.dto.anime.schedule

import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.domain.model.common.enum.WeekDay
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScheduleDTO(
    val monday: List<AnimeLightDTO>,
    val tuesday: List<AnimeLightDTO>,
    val wednesday: List<AnimeLightDTO>,
    val thursday: List<AnimeLightDTO>,
    val friday: List<AnimeLightDTO>,
    val saturday: List<AnimeLightDTO>,
    val sunday: List<AnimeLightDTO>,
) {
    fun getListByDay(dayOfWeek: WeekDay): List<AnimeLightDTO> {
        return when (dayOfWeek) {
            WeekDay.MONDAY -> monday
            WeekDay.TUESDAY -> tuesday
            WeekDay.WEDNESDAY -> wednesday
            WeekDay.THURSDAY -> thursday
            WeekDay.FRIDAY -> friday
            WeekDay.SATURDAY -> saturday
            WeekDay.SUNDAY -> sunday
        }
    }
}