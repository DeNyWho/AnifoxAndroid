@file:UseSerializers(LocalDateTimeSerializer::class, LocalDateSerializer::class)
package club.anifox.android.data.network.models.dto.anime.detail

import club.anifox.android.common.serializer.LocalDateSerializer
import club.anifox.android.common.serializer.LocalDateTimeSerializer
import club.anifox.android.data.network.models.dto.anime.common.AnimeGenreDTO
import club.anifox.android.data.network.models.dto.anime.common.AnimeImageDTO
import club.anifox.android.data.network.models.dto.anime.common.AnimeStudioDTO
import club.anifox.android.data.network.models.dto.anime.episodes.AnimeTranslationDTO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class AnimeDetailsDTO(
    @SerialName("title")
    val title: String = "",
    @SerialName("image")
    val image: AnimeImageDTO = AnimeImageDTO(),
    @SerialName("player_link")
    val playerLink: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("type")
    val type: String = "",
    @SerialName("rating_mpa")
    val ratingMpa: String = "",
    @SerialName("minimal_age")
    val minimalAge: Int = 0,
    @SerialName("year")
    val year: Int = 0,
    @SerialName("status")
    val status: String = "",
    @SerialName("season")
    val season: String = "",
    @SerialName("episodes")
    val episodes: Int? = null,
    @SerialName("episodes_aired")
    val episodesAired: Int = 0,
    @SerialName("next_episode_on")
    val nextEpisode: LocalDateTime? = null,
    @SerialName("released_on")
    val releasedOn: LocalDate? = null,
    @SerialName("aired_on")
    val airedOn: LocalDate = LocalDate.now(),
    @SerialName("description")
    val description: String? = null,
    @SerialName("other_title")
    val titleOther: List<String>? = null,
    @SerialName("english")
    val titleEnglish: List<String>? = null,
    @SerialName("japanese")
    val titleJapan: List<String>? = null,
    val synonyms: List<String> = listOf(),
    val studio: List<AnimeStudioDTO> = listOf(),
    val genres: List<AnimeGenreDTO> = listOf(),
    val translations: List<AnimeTranslationDTO>? = null,
)
