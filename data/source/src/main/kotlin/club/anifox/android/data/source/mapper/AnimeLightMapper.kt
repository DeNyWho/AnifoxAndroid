package club.anifox.android.data.source.mapper

import club.anifox.android.data.local.cache.model.anime.search.AnimeSearchEntity
import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.data.network.mappers.anime.common.toImage
import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeLightDTO.toLight(): AnimeLight = AnimeLight(
    title,
    image.toImage().medium,
    url,
    type,
    rating,
    year,
    status,
    season
)

fun AnimeLightDTO.toEntityLight(): AnimeEntity = AnimeEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
)

fun AnimeLightDTO.toEntityCacheSearchLight(): AnimeSearchEntity = AnimeSearchEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description
)