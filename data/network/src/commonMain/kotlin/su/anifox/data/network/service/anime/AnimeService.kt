package su.anifox.data.network.service.anime

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import su.anifox.graphql.AnimeQuery
import su.anifox.graphql.AnimesQuery
import su.anifox.graphql.type.AnimeOrder
import su.anifox.graphql.type.AnimeSeason
import su.anifox.graphql.type.AnimeStatus
import su.anifox.graphql.type.AnimeType
import su.anifox.graphql.type.RatingMpa
import su.anifox.graphql.type.SortOrder

class AnimeService(private val client: ApolloClient) {
    suspend fun getAnime(
        genres: List<String>?,
        limit: Int?,
        minimalAge: Int?,
        order: AnimeOrder?,
        page: Int?,
        ratingMpa: RatingMpa?,
        search: String?,
        season: AnimeSeason?,
        sort: SortOrder?,
        status: AnimeStatus?,
        studios: List<String>?,
        translations: List<Int>?,
        type: AnimeType?,
        years: List<Int>?,
    ): ApolloResponse<AnimesQuery.Data> {
        return client
            .query()
    }

    suspend fun getAnimeDetail(url: String): ApolloResponse<AnimeQuery.Data> {
        return client
            .query(AnimeQuery(url = url, userId = null))
            .execute()
    }
}