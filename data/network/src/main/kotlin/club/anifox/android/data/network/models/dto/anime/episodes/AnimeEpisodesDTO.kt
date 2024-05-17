package club.anifox.android.data.network.models.dto.anime.episodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeEpisodesDTO(
    @SerialName("title")
    val title: String = "",
    @SerialName("number")
    val number: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("translations")
    val translations: List<AnimeTranslationDTO> = listOf(),
)
