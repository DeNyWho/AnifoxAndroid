package club.anifox.android.data.network.models.dto.anime.schedule

import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
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
)