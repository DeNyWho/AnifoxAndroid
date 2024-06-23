package club.anifox.android.data.network.mappers.anime.videos

import club.anifox.android.data.network.models.dto.anime.videos.AnimeVideosDTO
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight

fun AnimeVideosDTO.toLight(): AnimeVideosLight = AnimeVideosLight(
    url = url,
    imageUrl = imageUrl,
    playerUrl = playerUrl,
    name = name,
    type = type,
)