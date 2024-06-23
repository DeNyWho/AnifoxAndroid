package club.anifox.android.data.network.models.dto.anime.videos

import club.anifox.android.domain.model.anime.enum.VideoType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeVideosDTO(
    @SerialName("url")
    val url: String,
    @SerialName("imageUrl")
    val imageUrl: String,
    @SerialName("playerUrl")
    val playerUrl: String,
    @SerialName("name")
    val name: String,
    @SerialName("type")
    val type: VideoType,
)
