package club.anifox.android.data.network.mappers.anime.episodes

import club.anifox.android.data.network.models.dto.anime.episodes.AnimeEpisodesTranslationsDTO
import club.anifox.android.domain.model.anime.translations.AnimeEpisodeTranslation

fun AnimeEpisodesTranslationsDTO.toLight(): AnimeEpisodeTranslation =
    AnimeEpisodeTranslation(id, link, title)