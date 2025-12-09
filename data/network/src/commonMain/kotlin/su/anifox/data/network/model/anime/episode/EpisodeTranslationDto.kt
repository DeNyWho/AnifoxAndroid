package su.anifox.data.network.model.anime.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeTranslationDto(
    @SerialName("id")
    val id: String,
    @SerialName("translationId")
    val translationId: Int,
    @SerialName("title")
    val title: String,
    @SerialName("type")
    val type: String,
    @SerialName("kodikPlayerLink")
    val kodikPlayerLink: String,
)