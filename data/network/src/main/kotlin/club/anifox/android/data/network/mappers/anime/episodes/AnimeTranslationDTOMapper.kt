package club.anifox.android.data.network.mappers.anime.episodes

import club.anifox.android.data.network.models.dto.anime.episodes.AnimeTranslationDTO
import club.anifox.android.domain.model.anime.translations.AnimeTranslation

fun AnimeTranslationDTO.toTranslation(): AnimeTranslation = AnimeTranslation(id, title, voice)