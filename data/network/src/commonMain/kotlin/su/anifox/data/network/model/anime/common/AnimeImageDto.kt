package su.anifox.data.network.model.anime.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeImageDto(
    @SerialName("large")
    val large: String? = null,
    @SerialName("medium")
    val medium: String? = null,
)