package club.anifox.android.data.network.mappers.anime.detail

import club.anifox.android.data.network.mappers.anime.common.toGenre
import club.anifox.android.data.network.mappers.anime.common.toImage
import club.anifox.android.data.network.mappers.anime.common.toStudio
import club.anifox.android.data.network.models.dto.anime.detail.AnimeDetailDTO
import club.anifox.android.domain.model.anime.AnimeDetail

fun AnimeDetailDTO.toDetail(): AnimeDetail = AnimeDetail(
    url = url,
    title = title,
    image = image.toImage(),
    type = type,
    rating = rating,
    ratingMpa = ratingMpa,
    minimalAge = minimalAge,
    year = year,
    status = status,
    season = season,
    episodes = episodes,
    episodesAired = episodesAired,
    nextEpisode = nextEpisode,
    releasedOn = releasedOn,
    airedOn = airedOn,
    titleEnglish = titleEnglish,
    titleJapan = titleJapan,
    titleOther = titleOther,
    synonyms = synonyms,
    description = description,
    genre = genres.map { it.toGenre() },
    studio = studio.map { it.toStudio() },
)