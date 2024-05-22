package club.anifox.android.data.network.mappers.anime.common

import club.anifox.android.data.network.models.dto.anime.common.AnimeImageDTO
import club.anifox.android.domain.model.anime.image.AnimeImage

fun AnimeImageDTO.toImage(): AnimeImage = AnimeImage(large, medium)