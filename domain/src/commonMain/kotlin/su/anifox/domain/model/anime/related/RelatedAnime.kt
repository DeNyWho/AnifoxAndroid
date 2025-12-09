package su.anifox.domain.model.anime.related

import androidx.compose.runtime.Immutable
import su.anifox.domain.model.anime.common.AnimeLight

@Immutable
data class RelatedAnime(
    val anime: AnimeLight,
    val relationType: String,
)