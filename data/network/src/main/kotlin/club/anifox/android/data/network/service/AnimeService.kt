package club.anifox.android.data.network.service

import club.anifox.android.data.network.api.ApiEndpoints
import club.anifox.android.data.network.api.ApiEndpoints.ANIME_EPISODES
import club.anifox.android.data.network.models.dto.anime.characters.AnimeCharactersLightDTO
import club.anifox.android.data.network.models.dto.anime.common.AnimeGenreDTO
import club.anifox.android.data.network.models.dto.anime.common.AnimeStudioDTO
import club.anifox.android.data.network.models.dto.anime.detail.AnimeDetailDTO
import club.anifox.android.data.network.models.dto.anime.episodes.AnimeEpisodesDTO
import club.anifox.android.data.network.models.dto.anime.episodes.AnimeTranslationCountDTO
import club.anifox.android.data.network.models.dto.anime.episodes.AnimeTranslationDTO
import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.data.network.models.dto.anime.related.AnimeRelatedDTO
import club.anifox.android.data.network.models.dto.anime.schedule.AnimeScheduleDTO
import club.anifox.android.data.network.models.dto.anime.videos.AnimeVideosDTO
import club.anifox.android.data.network.safeApiCall
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.common.request.Resource
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.parameter
import io.ktor.http.HttpMethod
import io.ktor.http.encodedPath
import javax.inject.Inject

class AnimeService @Inject constructor (private val client: HttpClient) {
    suspend fun getAnime(
        page: Int,
        limit: Int,
        status: AnimeStatus? = null,
        genres: List<String>? = null,
        searchQuery: String? = null,
        season: AnimeSeason? = null,
        ratingMpa: String? = null,
        minimalAge: Int? = null,
        type: AnimeType? = null,
        years: List<Int>? = null,
        studios: List<String>? = null,
        translation: List<Int>? = null,
        order: AnimeOrder? = null,
        sort: AnimeSort? = null,
    ): Resource<List<AnimeLightDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = ApiEndpoints.ANIME
                parameter("page", page)
                parameter("limit", limit)
                if (season != null) parameter("season", season.name)
                if (ratingMpa != null) parameter("rating_mpa", ratingMpa)
                if (minimalAge != null) parameter("age", minimalAge)
                if (type != null) parameter("type", type.name)
                if (status != null) parameter("status", status.name)
                if (order != null) parameter("order", order.name)
                if (sort != null) parameter("sort", sort.name)
                genres?.forEach { genre ->
                    parameter("genres", genre)
                }
                years?.forEach { year ->
                    parameter("year", year)
                }
                studios?.forEach { studio ->
                    parameter("studios", studio)
                }
                if (searchQuery != null) parameter("search", searchQuery)
                translation?.forEach { translation ->
                    parameter("translation", translation)
                }
            }
        }

        return safeApiCall<List<AnimeLightDTO>>(client, request)
    }

    suspend fun getAnimeGenres(): Resource<List<AnimeGenreDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/${ApiEndpoints.ANIME_GENRES}"
            }
        }

        return safeApiCall<List<AnimeGenreDTO>>(client, request)
    }

    suspend fun getAnimeYears(): Resource<List<Int>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/${ApiEndpoints.ANIME_YEARS}"
            }
        }

        return safeApiCall<List<Int>>(client, request)
    }

    suspend fun getAnimeStudios(): Resource<List<AnimeStudioDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/${ApiEndpoints.ANIME_STUDIOS}"
            }
        }

        return safeApiCall<List<AnimeStudioDTO>>(client, request)
    }

    suspend fun getAnimeTranslations(): Resource<List<AnimeTranslationDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/${ApiEndpoints.ANIME_TRANSLATIONS}"
            }
        }

        return safeApiCall<List<AnimeTranslationDTO>>(client, request)
    }

    suspend fun getAnimeTranslationsCount(url: String): Resource<List<AnimeTranslationCountDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ApiEndpoints.ANIME_TRANSLATIONS}/${ApiEndpoints.ANIME_TRANSLATIONS_COUNT}"
            }
        }

        return safeApiCall<List<AnimeTranslationCountDTO>>(client, request)
    }

    suspend fun getAnimeEpisodes(page: Int, limit: Int, url: String, translationId: Int): Resource<List<AnimeEpisodesDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ANIME_EPISODES}"
                parameter("page", page)
                parameter("limit", limit)
                parameter("translation_id", translationId)
            }
        }

        return safeApiCall<List<AnimeEpisodesDTO>>(client, request)
    }

    suspend fun getAnimeDetail(url: String): Resource<AnimeDetailDTO> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url"
            }
        }

        return safeApiCall<AnimeDetailDTO>(client, request)
    }

    suspend fun getAnimeSimilar(url: String): Resource<List<AnimeLightDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ApiEndpoints.ANIME_SIMILAR}"
            }
        }

        return safeApiCall<List<AnimeLightDTO>>(client, request)
    }

    suspend fun getAnimeRelated(url: String): Resource<List<AnimeRelatedDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ApiEndpoints.ANIME_RELATED}"
            }
        }

        return safeApiCall<List<AnimeRelatedDTO>>(client, request)
    }

    suspend fun getAnimeScreenshots(url: String): Resource<List<String>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ApiEndpoints.ANIME_SCREENSHOTS}"
            }
        }

        return safeApiCall<List<String>>(client, request)
    }

    suspend fun getAnimeVideos(url: String, type: VideoType?): Resource<List<AnimeVideosDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ApiEndpoints.ANIME_VIDEOS}"
                if (type != null) parameter("type", type.name)
            }
        }

        return safeApiCall<List<AnimeVideosDTO>>(client, request)
    }

    suspend fun getAnimeCharacters(
        page: Int,
        limit: Int,
        url: String,
    ): Resource<List<AnimeCharactersLightDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/$url/${ApiEndpoints.ANIME_CHARACTERS}"
            }
            parameter("page", page)
            parameter("limit", limit)
        }

        return safeApiCall<List<AnimeCharactersLightDTO>>(client, request)
    }

    suspend fun getAnimeSchedule(
        page: Int,
        limit: Int,
    ): Resource<AnimeScheduleDTO> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/${ApiEndpoints.ANIME_SCHEDULE}"
                parameter("page", page)
                parameter("limit", limit)
            }
        }

        return safeApiCall<AnimeScheduleDTO>(client, request)
    }
}