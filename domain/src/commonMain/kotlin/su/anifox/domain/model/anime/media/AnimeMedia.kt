package su.anifox.domain.model.anime.media

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class AnimeMedia(
    val animeId: String,
    val screenshots: ImmutableList<String>,
    val videos: ImmutableList<AnimeVideo>,
)