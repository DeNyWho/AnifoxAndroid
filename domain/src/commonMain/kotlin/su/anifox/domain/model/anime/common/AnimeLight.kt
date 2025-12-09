package su.anifox.domain.model.anime.common

import androidx.compose.runtime.Immutable
import su.anifox.domain.model.anime.enum.AnimeSeason
import su.anifox.domain.model.anime.enum.AnimeStatus
import su.anifox.domain.model.anime.enum.AnimeType

@Immutable
data class AnimeLight(
    val id: String,
    val url: String,
    val title: String,
    val image: String,
    val type: AnimeType,
    val status: AnimeStatus,
    val rating: Double?,
    val year: Int,
    val season: AnimeSeason,
    val episodesCount: Int?,
    val episodesAired: Int,
)