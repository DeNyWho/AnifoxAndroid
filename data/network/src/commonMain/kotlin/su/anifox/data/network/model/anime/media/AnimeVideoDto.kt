package su.anifox.data.network.model.anime.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeVideoDto(
    @SerialName("id")
    val id: String,
    @SerialName("name")
    val name: String? = null,
    @SerialName("url")
    val url: String,
    @SerialName("playerUrl")
    val playerUrl: String,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
    @SerialName("type")
    val type: String,
)
