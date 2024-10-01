package club.anifox.android.data.local.mappers.cache.anime

import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeCacheSearchEntity.toLight(): AnimeLight = AnimeLight(
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

fun AnimeCacheCatalogEntity.toLight(): AnimeLight = AnimeLight(
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

fun AnimeCacheGenresEntity.toLight(): AnimeLight = AnimeLight(
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