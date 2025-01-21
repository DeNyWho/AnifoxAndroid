package club.anifox.android.domain.model.anime

import androidx.compose.runtime.Immutable
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType

@Immutable
data class AnimeLight(
    val title: String,
    val image: String,
    val url: String,
    val type: AnimeType = AnimeType.Tv,
    val rating: Double? = null,
    val year: Int = 0,
    val status: AnimeStatus = AnimeStatus.Ongoing,
    val season: AnimeSeason = AnimeSeason.Fall,
    val description: String = "",
    val episodes: Int = 0,
    val episodesAired: Int = 0,
)
