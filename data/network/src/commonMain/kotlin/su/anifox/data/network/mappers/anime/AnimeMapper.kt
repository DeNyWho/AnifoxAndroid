package su.anifox.data.network.mappers.anime

import kotlinx.collections.immutable.toPersistentList
import kotlinx.datetime.LocalDateTime
import su.anifox.data.network.model.anime.common.AnimeDetailDto
import su.anifox.data.network.model.anime.common.AnimeGenreDto
import su.anifox.data.network.model.anime.common.AnimeImageDto
import su.anifox.data.network.model.anime.common.AnimeLightDto
import su.anifox.data.network.model.anime.common.AnimeStudioDto
import su.anifox.data.network.model.anime.compote.AnimeCompoteDTO
import su.anifox.data.network.model.anime.episode.AnimeEpisodeDto
import su.anifox.data.network.model.anime.episode.AnimeTranslationDto
import su.anifox.data.network.model.anime.episode.EpisodeTranslationDto
import su.anifox.data.network.model.anime.media.AnimeMediaDto
import su.anifox.data.network.model.anime.media.AnimeVideoDto
import su.anifox.data.network.model.anime.rating.AnimeRatingScoreDto
import su.anifox.data.network.model.anime.related.AnimeRelationsDto
import su.anifox.data.network.model.anime.related.RelatedAnimeDto
import su.anifox.data.network.model.anime.stats.AnimeStatisticsDto
import su.anifox.domain.model.anime.common.AnimeDetail
import su.anifox.domain.model.anime.common.AnimeGenre
import su.anifox.domain.model.anime.common.AnimeImage
import su.anifox.domain.model.anime.common.AnimeLight
import su.anifox.domain.model.anime.common.AnimeStudio
import su.anifox.domain.model.anime.compote.AnimeCompote
import su.anifox.domain.model.anime.enum.AnimeSeason
import su.anifox.domain.model.anime.enum.AnimeStatus
import su.anifox.domain.model.anime.enum.AnimeType
import su.anifox.domain.model.anime.enum.TranslationType
import su.anifox.domain.model.anime.enum.VideoType
import su.anifox.domain.model.anime.episodes.AnimeEpisode
import su.anifox.domain.model.anime.episodes.translations.AnimeEpisodeTranslation
import su.anifox.domain.model.anime.episodes.translations.AnimeTranslation
import su.anifox.domain.model.anime.media.AnimeMedia
import su.anifox.domain.model.anime.media.AnimeVideo
import su.anifox.domain.model.anime.rating.AnimeRatingScore
import su.anifox.domain.model.anime.related.AnimeRelations
import su.anifox.domain.model.anime.related.RelatedAnime
import su.anifox.domain.model.anime.stats.AnimeStatistics

object AnimeMapper {
    fun AnimeLightDto.toDomain(): AnimeLight {
        return AnimeLight(
            id = id,
            url = url,
            title = title,
            image = image.medium ?: image.large ?: "",
            type = AnimeType.valueOf(type),
            status = AnimeStatus.valueOf(status),
            rating = rating,
            year = year,
            season = AnimeSeason.valueOf(season),
            episodesCount = episodesCount,
            episodesAired = episodesAired,
        )
    }
    
    fun AnimeDetailDto.toDomain(): AnimeDetail {
        return AnimeDetail(
            id = id,
            url = url,
            title = title,
            titleEnglish = titlesEnglish.toPersistentList(),
            titleJapan = titlesJapan.toPersistentList(),
            titlesOther = titlesOther.toPersistentList(),
            titlesSynonyms = titlesSynonyms.toPersistentList(),
            image = image.toDomain(),
            type = AnimeType.valueOf(type),
            status = AnimeStatus.valueOf(status),
            rating = rating,
            ratingCount = ratingCount,
            ratingMpa = ratingMpa,
            minimalAge = minimalAge,
            year = year,
            season = AnimeSeason.valueOf(season),
            episodesCount = episodesCount,
            episodesAired = episodesAired,
            duration = duration,
            nextEpisode = nextEpisode?.let { LocalDateTime.parse(it) },
            releasedOn = LocalDateTime.parse(releasedOn),
            airedOn = airedOn?.let { LocalDateTime.parse(it) },
            description = description,
            accentColor = accentColor,
            playerLink = playerLink,
            createdAt = LocalDateTime.parse(createdAt),
            updatedAt = updatedAt?.let { LocalDateTime.parse(it) },
        )
    }
    
    fun AnimeImageDto.toDomain(): AnimeImage {
        return AnimeImage(
            large = large ?: "",
            medium = medium ?: "",
        )
    }
    
    fun AnimeMediaDto.toDomain(animeId: String): AnimeMedia {
        return AnimeMedia(
            animeId = animeId,
            screenshots = screenshots.toPersistentList(),
            videos = videos.map { it.toDomain() }.toPersistentList(),
        )
    }
    
    fun AnimeVideoDto.toDomain(): AnimeVideo {
        return AnimeVideo(
            id = id,
            name = name,
            url = url,
            playerUrl = playerUrl,
            imageUrl = imageUrl,
            type = VideoType.valueOf(type),
        )
    }
    
    fun AnimeStatisticsDto.toDomain(
        animeId: String,
        ratingDistribution: List<AnimeRatingScoreDto>
    ): AnimeStatistics {
        return AnimeStatistics(
            animeId = animeId,
            shikimoriId = shikimoriId,
            shikimoriRating = shikimoriRating,
            shikimoriVotes = shikimoriVotes,
            ratingDistribution = ratingDistribution.map { it.toDomain() }.toPersistentList(),
        )
    }
    
    fun AnimeRatingScoreDto.toDomain(): AnimeRatingScore {
        return AnimeRatingScore(
            score = score,
            votes = votes,
        )
    }
    
    fun AnimeCompoteDTO.toDomain(animeId: String): AnimeCompote {
        return AnimeCompote(
            animeId = animeId,
            genres = genres.map { it.toDomain() }.toPersistentList(),
            studios = studios.map { it.toDomain() }.toPersistentList(),
        )
    }
    
    fun AnimeGenreDto.toDomain(): AnimeGenre {
        return AnimeGenre(
            id = id,
            name = name,
            image = image ?: "",
        )
    }
    
    fun AnimeStudioDto.toDomain(): AnimeStudio {
        return AnimeStudio(
            id = id,
            name = name,
        )
    }
    
    fun AnimeRelationsDto.toDomain(): AnimeRelations {
        return AnimeRelations(
            franchise = franchise,
            related = related.data.map { it.toDomain() }.toPersistentList(),
            similar = similar.data.map { it.toDomain() }.toPersistentList(),
        )
    }
    
    fun RelatedAnimeDto.toDomain(): RelatedAnime {
        return RelatedAnime(
            anime = anime.toDomain(),
            relationType = type,
        )
    }

    fun AnimeEpisodeDto.toDomain(): AnimeEpisode {
        return AnimeEpisode(
            id = id,
            animeId = animeId,
            number = number,
            title = title,
            titleEn = titleEn,
            description = description,
            descriptionEn = descriptionEn,
            image = image,
            aired = aired?.let { LocalDateTime.parse(it) },
            duration = duration,
            filler = filler,
            recap = recap,
            translations = translations.map { it.toDomain() }.toPersistentList(),
        )
    }

    fun EpisodeTranslationDto.toDomain(): AnimeEpisodeTranslation {
        return AnimeEpisodeTranslation(
            id = translationId,
            link = kodikPlayerLink,
            title = title,
            type = TranslationType.valueOf(type),
        )
    }

    fun AnimeTranslationDto.toDomain(): AnimeTranslation {
        return AnimeTranslation(
            id = id,
            title = title,
            type = TranslationType.valueOf(type),
        )
    }
}