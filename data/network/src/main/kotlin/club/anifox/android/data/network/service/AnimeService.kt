package club.anifox.android.data.network.service

import club.anifox.android.data.network.api.ApiEndpoints
import club.anifox.android.data.network.models.dto.anime.light.AnimeLightDTO
import club.anifox.android.data.network.safeApiCall
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.model.common.Resource
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
        status: AnimeStatus?,
        genres: List<String>?,
        searchQuery: String?,
        season: AnimeSeason?,
        ratingMpa: String?,
        minimalAge: String?,
        type: AnimeType?,
        year: Int?,
        studio: String?,
        filter: FilterEnum?,
    ): Resource<List<AnimeLightDTO>> {
        val request = HttpRequestBuilder().apply {
            method = HttpMethod.Get
            url {
                encodedPath = "${ApiEndpoints.ANIME}/"
                parameter("page", page)
                parameter("limit", limit)
                if (season != null) parameter("season", season.name)
                if (ratingMpa != null) parameter("rating_mpa", ratingMpa)
                if (minimalAge != null) parameter("age", minimalAge)
                if (year != null) parameter("year", year)
                if (type != null) parameter("type", type.name)
                if (status != null) parameter("status", status.name)
                if (genres != null) if (genres.isNotEmpty()) parameter("genres", genres)
                if (studio != null) parameter("studio", studio)
                if (searchQuery != null) parameter("searchQuery", searchQuery)
                if (filter != null) parameter("filter", filter.name)
            }
        }

        return safeApiCall<List<AnimeLightDTO>>(client, request)
    }
}