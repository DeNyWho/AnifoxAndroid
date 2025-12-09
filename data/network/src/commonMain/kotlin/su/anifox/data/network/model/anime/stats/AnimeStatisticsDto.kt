package su.anifox.data.network.model.anime.stats

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeStatisticsDto(
    @SerialName("shikimoriId")
    val shikimoriId: Int,
    @SerialName("shikimoriRating")
    val shikimoriRating: Double,
    @SerialName("shikimoriVotes")
    val shikimoriVotes: Int,
)