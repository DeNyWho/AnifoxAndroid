package club.anifox.android.data.network.models.dto.anime.schedule

import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.domain.model.common.enum.WeekDay
import kotlinx.serialization.Serializable

@Serializable
data class AnimeScheduleDTO(
    val monday: List<AnimeLightDTO>? = null,
    val tuesday: List<AnimeLightDTO>? = null,
    val wednesday: List<AnimeLightDTO>? = null,
    val thursday: List<AnimeLightDTO>? = null,
    val friday: List<AnimeLightDTO>? = null,
    val saturday: List<AnimeLightDTO>? = null,
    val sunday: List<AnimeLightDTO>? = null,
) {
    fun getListByDay(dayOfWeek: WeekDay): List<AnimeLightDTO> {
        return when (dayOfWeek) {
            WeekDay.MONDAY -> monday.orEmpty()
            WeekDay.TUESDAY -> tuesday.orEmpty()
            WeekDay.WEDNESDAY -> wednesday.orEmpty()
            WeekDay.THURSDAY -> thursday.orEmpty()
            WeekDay.FRIDAY -> friday.orEmpty()
            WeekDay.SATURDAY -> saturday.orEmpty()
            WeekDay.SUNDAY -> sunday.orEmpty()
        }
    }
}