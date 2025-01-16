package club.anifox.android.data.local.mappers.anime

import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.data.local.model.anime.common.AnimeImageEntity
import club.anifox.android.domain.model.anime.AnimeDetail
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun AnimeDetail.toEntities(): Pair<AnimeEntity, AnimeImageEntity> {
    val animeEntity = AnimeEntity(
        url = url,
        title = title,
        type = type,
        rating = rating,
        ratingMpa = ratingMpa,
        minimalAge = minimalAge,
        year = year,
        status = status,
        season = season,
        description = description ?: "",
        episodes = episodes,
        episodesAired = episodesAired,
        nextEpisode = nextEpisode,
        titleOther = titleOther,
        titleEnglish = titleEnglish,
        titleJapan = titleJapan,
        synonyms = synonyms,
        releasedOn = releasedOn,
        airedOn = airedOn,
        genres = genres.joinToString(",") { it.name },
        studios = studios.joinToString(",") { it.name }
    )

    val imageEntity = AnimeImageEntity(
        animeUrl = url,
        large = image.large,
        medium = image.medium
    )

    return animeEntity to imageEntity
}