package su.anifox.data.network.model.anime.rating

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeRatingDistributionDto(
    @SerialName("animeId")
    val animeId: String,
    @SerialName("scores")
    val scores: List<AnimeRatingScoreDto>,
    @SerialName("total")
    val total: Int,
)