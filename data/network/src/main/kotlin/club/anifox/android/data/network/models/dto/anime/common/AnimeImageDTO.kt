package club.anifox.android.data.network.models.dto.anime.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeImageDTO(
    @SerialName("large")
    val large: String = "",
    @SerialName("medium")
    val medium: String = "",
)