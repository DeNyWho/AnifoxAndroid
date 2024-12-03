package club.anifox.android.data.local.mappers.anime

import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeEntity.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    episodes = episodes,
    episodesAired = episodesAired,
)