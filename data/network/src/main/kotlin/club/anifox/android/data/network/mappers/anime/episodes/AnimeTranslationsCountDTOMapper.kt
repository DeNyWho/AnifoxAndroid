package club.anifox.android.data.network.mappers.anime.episodes

import club.anifox.android.data.network.models.dto.anime.episodes.AnimeTranslationCountDTO
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount

fun AnimeTranslationCountDTO.toTranslationsCount(): AnimeTranslationsCount =
    AnimeTranslationsCount(translation.toTranslation(), countEpisodes, link)