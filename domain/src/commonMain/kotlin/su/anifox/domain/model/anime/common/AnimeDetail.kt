package su.anifox.domain.model.anime.common

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.LocalDateTime
import su.anifox.domain.model.anime.enum.AnimeSeason
import su.anifox.domain.model.anime.enum.AnimeStatus
import su.anifox.domain.model.anime.enum.AnimeType

@Immutable
data class AnimeDetail(
    val id: String,
    val url: String,
    val title: String,
    val titleEnglish: ImmutableList<String>,
    val titleJapan: ImmutableList<String>,
    val titlesOther: ImmutableList<String>,
    val titlesSynonyms: ImmutableList<String>,
    val image: AnimeImage,
    val type: AnimeType,
    val status: AnimeStatus,
    val rating: Double,
    val ratingCount: Int,
    val ratingMpa: String,
    val minimalAge: Int,
    val year: Int,
    val season: AnimeSeason,
    val episodesCount: Int?,
    val episodesAired: Int,
    val duration: Double?,
    val nextEpisode: LocalDateTime?,
    val releasedOn: LocalDateTime,
    val airedOn: LocalDateTime?,
    val description: String?,
    val accentColor: String,
    val playerLink: String,
    val franchise: String? = null,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
)