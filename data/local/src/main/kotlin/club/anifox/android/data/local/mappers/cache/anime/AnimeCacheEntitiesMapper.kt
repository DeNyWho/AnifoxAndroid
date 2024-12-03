package club.anifox.android.data.local.mappers.cache.anime

import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodeWithTranslations
import club.anifox.android.data.local.cache.model.anime.catalog.AnimeCacheCatalogEntity
import club.anifox.android.data.local.cache.model.anime.genres.AnimeCacheGenresEntity
import club.anifox.android.data.local.cache.model.anime.schedule.AnimeCacheScheduleEntity
import club.anifox.android.data.local.cache.model.anime.search.AnimeCacheSearchEntity
import club.anifox.android.data.local.cache.model.anime.translation.AnimeCacheEpisodesTranslationsEntity
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.anime.translations.AnimeEpisodeTranslation

fun AnimeCacheSearchEntity.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image,
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

fun AnimeCacheScheduleEntity.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image,
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

fun AnimeCacheCatalogEntity.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image,
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

fun AnimeCacheGenresEntity.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image,
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

fun AnimeCacheEpisodeWithTranslations.toLight(): AnimeEpisodesLight = AnimeEpisodesLight(
    title = episode.title,
    number = episode.number,
    image = episode.image,
    aired = episode.aired,
    filler = episode.filler,
    recap = episode.recap,
    translation = translations.map { it.toTranslation() },
)

fun AnimeCacheEpisodesTranslationsEntity.toTranslation(): AnimeEpisodeTranslation = AnimeEpisodeTranslation(
    id = translationId,
    link = link,
    title = translationTitle,
)
