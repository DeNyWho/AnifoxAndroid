package club.anifox.android.data.local.mappers.cache.anime

import club.anifox.android.data.local.cache.model.anime.search.AnimeSearchEntity
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeSearchEntity.toLight(): AnimeLight = AnimeLight(
    title,
    image,
    url,
    type,
    rating,
    year,
    status,
    season,
    description,
)