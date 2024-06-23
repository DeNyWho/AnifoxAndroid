package club.anifox.android.data.network.mappers.anime.light

import club.anifox.android.data.network.mappers.anime.common.toImage
import club.anifox.android.data.network.models.dto.anime.related.AnimeRelatedDTO
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight

fun AnimeRelatedDTO.toLight(): AnimeRelatedLight = AnimeRelatedLight(
    anime = AnimeLight(
        anime.title,
        anime.image.toImage().medium,
        anime.url,
        anime.type,
        anime.rating,
        anime.year,
        anime.status,
        anime.season,
    ),
    type = relation.type,
)