package su.anifox.data.network.model.anime.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeLightDto(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("title")
    val title: String,
    @SerialName("image")
    val image: AnimeImageDto,
    @SerialName("type")
    val type: String,
    @SerialName("status")
    val status: String,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("year")
    val year: Int,
    @SerialName("season")
    val season: String,
    @SerialName("episodesCount")
    val episodesCount: Int? = null,
    @SerialName("episodesAired")
    val episodesAired: Int,
)