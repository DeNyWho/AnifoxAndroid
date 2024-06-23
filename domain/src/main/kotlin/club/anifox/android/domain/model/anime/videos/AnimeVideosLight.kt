package club.anifox.android.domain.model.anime.videos

import club.anifox.android.domain.model.anime.enum.VideoType

data class AnimeVideosLight(
    val url: String,
    val imageUrl: String,
    val playerUrl: String,
    val name: String,
    val type: VideoType,
)