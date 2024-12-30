package club.anifox.android.data.network.mappers.anime.related

import club.anifox.android.data.network.mappers.anime.common.toImage
import club.anifox.android.data.network.models.dto.anime.related.AnimeRelatedDTO
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight

fun AnimeRelatedDTO.toLight(): AnimeRelatedLight = AnimeRelatedLight(
    anime = AnimeLight(
        title = anime.title,
        image = anime.image.toImage().medium,
        url = anime.url,
        type = anime.type,
        rating = anime.rating,
        year = anime.year,
        status = anime.status,
        season = anime.season,
        episodes = anime.episodes,
        episodesAired = anime.episodesAired,
    ),
    type = relation.type,
)