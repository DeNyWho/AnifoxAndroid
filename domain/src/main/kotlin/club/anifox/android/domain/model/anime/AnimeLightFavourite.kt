package club.anifox.android.domain.model.anime

import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import java.time.LocalDateTime

data class AnimeLightFavourite(
    val title: String,
    val image: String,
    val url: String,
    val type: AnimeType = AnimeType.Tv,
    val rating: Double? = null,
    val year: Int = 0,
    val status: AnimeStatus = AnimeStatus.Ongoing,
    val season: AnimeSeason = AnimeSeason.Fall,
    val description: String = "",
    val lastWatchedEpisode: Int = 0,
    val addedAt: LocalDateTime = LocalDateTime.now(),
)
