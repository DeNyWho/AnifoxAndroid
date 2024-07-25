package club.anifox.android.data.local.mappers.anime

import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeEntity.toLight(): AnimeLight = AnimeLight(
    title,
    image,
    url,
    type,
    rating,
    year,
    status,
    season
)