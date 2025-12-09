package su.anifox.domain.model.anime.media

import androidx.compose.runtime.Immutable
import su.anifox.domain.model.anime.enum.VideoType

@Immutable
data class AnimeVideo(
    val id: String,
    val name: String? = null,
    val url: String,
    val playerUrl: String,
    val imageUrl: String? = null,
    val type: VideoType,
)