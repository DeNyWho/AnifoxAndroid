package club.anifox.android.data.network.models.dto.anime.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeStudioDTO(
    @SerialName("id")
    val id: String = "",
    @SerialName("name")
    val name: String = "",
)