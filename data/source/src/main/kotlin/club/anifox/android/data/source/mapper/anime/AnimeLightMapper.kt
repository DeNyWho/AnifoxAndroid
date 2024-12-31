package club.anifox.android.data.source.mapper.anime

import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.data.network.mappers.anime.common.toImage
import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.enum.WeekDay

fun AnimeLightDTO.toEntityLight(): AnimeEntity = AnimeEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description,
    episodes = episodes,
    episodesAired = episodesAired,
)

fun AnimeLightDTO.toEntityCacheSearchLight(): AnimeCacheSearchEntity = AnimeCacheSearchEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description,
    episodes = episodes,
    episodesAired = episodesAired,
)

fun AnimeLightDTO.toEntityCacheCatalogLight(): AnimeCacheCatalogEntity = AnimeCacheCatalogEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description,
    episodes = episodes,
    episodesAired = episodesAired,
)

fun AnimeLightDTO.toEntityCacheGenresLight(): AnimeCacheGenresEntity = AnimeCacheGenresEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description,
    episodes = episodes,
    episodesAired = episodesAired,
)

fun AnimeLightDTO.toEntityCacheScheduleLight(dayOfWeek: WeekDay): AnimeCacheScheduleEntity = AnimeCacheScheduleEntity(
    title = title,
    image = image.toImage().medium,
    url = url,
    dayOfWeek = dayOfWeek,
    type = type,
    rating = rating,
    year = year,
    status = status,
    season = season,
    description = description,
    episodes = episodes,
    episodesAired = episodesAired,
)