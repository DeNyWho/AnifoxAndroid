package club.anifox.android.data.network.models.dto.anime.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeMediaDTO(
    @SerialName("url")
    val url: String = "",
    @SerialName("image")
    val imageUrl: String = "",
    @SerialName("player_url")
    val playerUrl: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("kind")
    val kind: String = "",
    @SerialName("hosting")
    val hosting: String = "",
)
