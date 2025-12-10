package su.anifox.data.network.model.anime.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AnimeDetailDto(
    @SerialName("id")
    val id: String,
    @SerialName("url")
    val url: String,
    @SerialName("title")
    val title: String,
    @SerialName("titlesEnglish")
    val titlesEnglish: List<String>,
    @SerialName("titlesJapan")
    val titlesJapan: List<String>,
    @SerialName("titlesOther")
    val titlesOther: List<String>,
    @SerialName("titlesSynonyms")
    val titlesSynonyms: List<String>,
    @SerialName("image")
    val image: AnimeImageDto,
    @SerialName("genres")
    val genres: List<AnimeGenreDto>,
    @SerialName("studios")
    val studios: List<AnimeStudioDto>,
    @SerialName("type")
    val type: String,
    @SerialName("status")
    val status: String,
    @SerialName("rating")
    val rating: Double,
    @SerialName("ratingCount")
    val ratingCount: Int,
    @SerialName("ratingMpa")
    val ratingMpa: String,
    @SerialName("minimalAge")
    val minimalAge: Int,
    @SerialName("year")
    val year: Int,
    @SerialName("season")
    val season: String,
    @SerialName("episodesCount")
    val episodesCount: Int? = null,
    @SerialName("episodesAired")
    val episodesAired: Int,
    @SerialName("duration")
    val duration: Double? = null,
    @SerialName("nextEpisode")
    val nextEpisode: String? = null,
    @SerialName("releasedOn")
    val releasedOn: String,
    @SerialName("airedOn")
    val airedOn: String? = null,
    @SerialName("description")
    val description: String? = null,
    @SerialName("accentColor")
    val accentColor: String,
    @SerialName("playerLink")
    val playerLink: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
)