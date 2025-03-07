@file:UseSerializers(LocalDateTimeSerializer::class, LocalDateSerializer::class)

package club.anifox.android.data.network.models.dto.anime.detail

import club.anifox.android.core.common.serializer.LocalDateSerializer
import club.anifox.android.core.common.serializer.LocalDateTimeSerializer
import club.anifox.android.data.network.models.dto.anime.common.AnimeGenreDTO
import club.anifox.android.data.network.models.dto.anime.common.AnimeImageDTO
import club.anifox.android.data.network.models.dto.anime.common.AnimeStudioDTO
import club.anifox.android.data.network.models.dto.anime.episodes.AnimeTranslationDTO
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.LocalDate
import java.time.LocalDateTime

@Serializable
data class AnimeDetailDTO(
    @SerialName("title")
    val title: String = "",
    @SerialName("image")
    val image: AnimeImageDTO = AnimeImageDTO(),
    @SerialName("player_link")
    val playerLink: String = "",
    @SerialName("url")
    val url: String = "",
    @SerialName("type")
    val type: AnimeType = AnimeType.Tv,
    @SerialName("rating_mpa")
    val ratingMpa: String = "",
    @SerialName("minimal_age")
    val minimalAge: Int = 0,
    @SerialName("rating")
    val rating: Double? = null,
    @SerialName("year")
    val year: Int = 0,
    @SerialName("status")
    val status: AnimeStatus = AnimeStatus.Ongoing,
    @SerialName("season")
    val season: AnimeSeason = AnimeSeason.Fall,
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
