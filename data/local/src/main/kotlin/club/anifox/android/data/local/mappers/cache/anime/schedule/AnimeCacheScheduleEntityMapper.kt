package club.anifox.android.data.local.mappers.cache.anime.schedule

import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeCacheScheduleEntity.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description
)