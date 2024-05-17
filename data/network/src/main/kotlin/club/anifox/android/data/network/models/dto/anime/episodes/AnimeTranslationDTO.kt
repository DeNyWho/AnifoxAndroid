package club.anifox.android.data.network.models.dto.anime.episodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeTranslationDTO(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("title")
    val title: String = "",
    @SerialName("voice")
    val voice: String = "",
)
