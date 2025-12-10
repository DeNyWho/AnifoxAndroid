package su.anifox.data.network.model.anime.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeStudioDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String,
)