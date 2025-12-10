package su.anifox.data.network.model.anime.rating

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeRatingScoreDto(
    @SerialName("score")
    val score: Int,
    @SerialName("votes")
    val votes: Int,
)