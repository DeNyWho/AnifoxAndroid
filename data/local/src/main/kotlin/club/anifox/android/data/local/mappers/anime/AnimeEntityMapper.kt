package club.anifox.android.data.local.mappers.anime

import club.anifox.android.data.local.model.anime.AnimeEntity
import club.anifox.android.domain.model.anime.AnimeDetail
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun AnimeDetail.toEntity(): AnimeEntity {
    return AnimeEntity(
        url = this.url,
        title = this.title,
        image = Json.encodeToString(this.image),
        type = this.type,
        rating = this.rating,
        ratingMpa = this.ratingMpa,
        minimalAge = this.minimalAge,
        year = this.year,
        status = this.status,
        season = this.season,
        description = this.description ?: "",
        lastWatchedEpisode = null, // Пока не передаем
        episodes = this.episodes,
        episodesAired = this.episodesAired,
        nextEpisode = this.nextEpisode,
        titleOther = this.titleOther,
        titleEnglish = this.titleEnglish,
        titleJapan = this.titleJapan,
        synonyms = this.synonyms,
        releasedOn = this.releasedOn,
        airedOn = this.airedOn,
        genres = Json.encodeToString(this.genres),
        studios = Json.encodeToString(this.studios),
    )
}

fun AnimeEntity.toDetail(): AnimeDetail {
    return AnimeDetail(
        url = this.url,
        title = this.title,
        image = Json.decodeFromString(this.image),
        type = this.type,
        rating = this.rating,
        ratingMpa = this.ratingMpa,
        minimalAge = this.minimalAge,
        year = this.year,
        status = this.status,
        season = this.season,
        description = this.description,
        episodes = this.episodes,
        episodesAired = this.episodesAired,
        nextEpisode = this.nextEpisode,
        titleOther = this.titleOther,
        titleEnglish = this.titleEnglish,
        titleJapan = this.titleJapan,
        synonyms = this.synonyms,
        releasedOn = this.releasedOn,
        airedOn = this.airedOn,
        genres = Json.decodeFromString(this.genres),
        studios = Json.decodeFromString(this.studios),
    )
}