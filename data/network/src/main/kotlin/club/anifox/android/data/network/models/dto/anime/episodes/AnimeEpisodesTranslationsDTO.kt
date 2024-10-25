package club.anifox.android.data.network.models.dto.anime.episodes

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AnimeEpisodesTranslationsDTO(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("link")
    val link: String = "",
    @SerialName("title")
    val title: String = "",
)
