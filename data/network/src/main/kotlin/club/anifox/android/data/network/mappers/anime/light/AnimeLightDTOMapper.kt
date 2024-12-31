package club.anifox.android.data.network.mappers.anime.light

import club.anifox.android.data.network.mappers.anime.common.toImage
import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.domain.model.anime.AnimeLight

fun AnimeLightDTO.toLight(): AnimeLight = AnimeLight(
    title = title,
    image = image.toImage().medium,
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