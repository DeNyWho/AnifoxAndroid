package su.anifox.data.network.model.anime.media

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeMediaDto(
    @SerialName("screenshots")
    val screenshots: List<String>,
    @SerialName("videos")
    val videos: List<AnimeVideoDto>,
)