package club.anifox.android.data.network.mappers.anime.episodes

import club.anifox.android.data.network.models.dto.anime.episodes.AnimeEpisodesDTO
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight

fun AnimeEpisodesDTO.toLight(): AnimeEpisodesLight = AnimeEpisodesLight(
    title,
    number,
    image,
    aired,
    description,
    filler,
    recap,
    translations.map { it.toLight() })