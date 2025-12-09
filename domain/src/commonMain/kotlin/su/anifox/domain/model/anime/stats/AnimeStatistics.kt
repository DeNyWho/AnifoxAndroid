package su.anifox.domain.model.anime.stats

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import su.anifox.domain.model.anime.rating.AnimeRatingScore

@Immutable
data class AnimeStatistics(
    val animeId: String,
    val shikimoriId: Int,
    val shikimoriRating: Double,
    val shikimoriVotes: Int,
    val ratingDistribution: ImmutableList<AnimeRatingScore>,
)