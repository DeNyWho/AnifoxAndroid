package club.anifox.android.data.local.mappers.cache.anime.episodes

import club.anifox.android.data.local.cache.dao.anime.episodes.AnimeCacheEpisodeWithTranslations
import club.anifox.android.data.local.cache.model.anime.translation.AnimeCacheEpisodesTranslationsEntity
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.anime.translations.AnimeEpisodeTranslation

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
    title = title,
)