package club.anifox.android.data.network.mappers.anime.common

import club.anifox.android.data.network.models.dto.anime.common.AnimeGenreDTO
import club.anifox.android.domain.model.anime.genre.AnimeGenre

fun AnimeGenreDTO.toGenre(): AnimeGenre = AnimeGenre(id, name)