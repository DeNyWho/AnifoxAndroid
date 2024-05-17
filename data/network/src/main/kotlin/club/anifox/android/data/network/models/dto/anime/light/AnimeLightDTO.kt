package club.anifox.android.data.network.models.dto.anime.light

import club.anifox.android.data.network.models.dto.anime.common.AnimeImageDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeLightDTO(
    @SerialName("title")
    val title: String = "",
    @SerialName("image")
    val image: AnimeImageDTO = AnimeImageDTO(),
    @SerialName("url")
    val url: String = "",
)
