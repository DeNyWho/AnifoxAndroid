package su.anifox.data.network.service.anime

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Optional
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQL
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLAnimeUrl
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLGenres
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLMinimalAge
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLOrder
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLRatingMpa
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLRole
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLSearch
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLSeason
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLSort
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLStatus
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLStudios
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLTranslations
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLType
import su.anifox.data.network.mappers.filter.FilterMapper.toGraphQLYears
import su.anifox.domain.model.anime.filter.AnimeFilters
import su.anifox.domain.model.anime.filter.CharacterFilters
import su.anifox.domain.model.common.enum.DayOfWeek
import su.anifox.graphql.AnimeCompoteQuery
import su.anifox.graphql.AnimeGenresQuery
import su.anifox.graphql.AnimeMediaQuery
import su.anifox.graphql.AnimeQuery
import su.anifox.graphql.AnimeRelationsQuery
import su.anifox.graphql.AnimeSchedulesQuery
import su.anifox.graphql.AnimeStatisticsQuery
import su.anifox.graphql.AnimeStudiosQuery
import su.anifox.graphql.AnimeYearsQuery
import su.anifox.graphql.AnimesQuery
import su.anifox.graphql.CharacterQuery
import su.anifox.graphql.CharactersQuery
import su.anifox.graphql.RandomAnimesQuery

class AnimeService(private val client: ApolloClient) {
    suspend fun getAnimes(
        filters: AnimeFilters
    ): ApolloResponse<AnimesQuery.Data> {
        return client
            .query(
                AnimesQuery(
                    genres = filters.toGraphQLGenres().getOrNull(),
                    studios = filters.toGraphQLStudios().getOrNull(),
                    years = filters.toGraphQLYears().getOrNull(),
                    translations = filters.toGraphQLTranslations().getOrNull(),
                    search = filters.toGraphQLSearch().getOrNull(),
                    season = filters.toGraphQLSeason().getOrNull(),
                    status = filters.toGraphQLStatus().getOrNull(),
                    type = filters.toGraphQLType().getOrNull(),
                    ratingMpa = filters.toGraphQLRatingMpa().getOrNull(),
                    minimalAge = filters.toGraphQLMinimalAge().getOrNull(),
                    order = filters.toGraphQLOrder().getOrNull(),
                    sort = filters.toGraphQLSort().getOrNull(),
                    page = Optional.present(filters.page),
                    limit = Optional.present(filters.limit),
                )
            )
            .execute()
    }

    suspend fun getAnimeDetail(
        url: String,
        userId: String? = null
    ): ApolloResponse<AnimeQuery.Data> {
        return client
            .query(
                AnimeQuery(
                    url = url,
                    userId = userId
                )
            )
            .execute()
    }

    suspend fun getAnimeMedia(
        url: String
    ): ApolloResponse<AnimeMediaQuery.Data> {
        return client
            .query(AnimeMediaQuery(url = url))
            .execute()
    }

    suspend fun getAnimeRelations(
        url: String,
        relatedLimit: Int = 20,
        similarLimit: Int = 20
    ): ApolloResponse<AnimeRelationsQuery.Data> {
        return client
            .query(
                AnimeRelationsQuery(
                    url = url,
                    relatedLimit = Optional.present(relatedLimit),
                    similarLimit = Optional.present(similarLimit),
                )
            )
            .execute()
    }

    suspend fun getAnimeStatistics(
        url: String,
        animeId: String
    ): ApolloResponse<AnimeStatisticsQuery.Data> {
        return client
            .query(
                AnimeStatisticsQuery(
                    url = url,
                    animeId = animeId
                )
            )
            .execute()
    }

    suspend fun getRandomAnimes(
        count: Int = 10
    ): ApolloResponse<RandomAnimesQuery.Data> {
        return client
            .query(
                RandomAnimesQuery(
                    count = Optional.present(count)
                )
            )
            .execute()
    }

    suspend fun getCharacters(
        filters: CharacterFilters
    ): ApolloResponse<CharactersQuery.Data> {
        return client
            .query(
                CharactersQuery(
                    animeUrl = filters.toGraphQLAnimeUrl().getOrThrow(),
                    search = filters.search,
                    role = filters.toGraphQLRole().getOrNull(),
                    page = filters.page,
                )
            )
            .execute()
    }

    suspend fun getCharacter(
        id: String? = null,
        malId: Int? = null,
        userId: String? = null,
    ): ApolloResponse<CharacterQuery.Data> {
        return client
            .query(
                CharacterQuery(
                    characterId = id,
                    malId = malId,
                    userId = userId,
                )
            )
            .execute()
    }

    suspend fun getCompote(
        url: String,
    ): ApolloResponse<AnimeCompoteQuery.Data> {
        return client
            .query(AnimeCompoteQuery(url))
            .execute()
    }

    suspend fun getGenres(): ApolloResponse<AnimeGenresQuery.Data> {
        return client
            .query(AnimeGenresQuery())
            .execute()
    }

    suspend fun getStudios(): ApolloResponse<AnimeStudiosQuery.Data> {
        return client
            .query(AnimeStudiosQuery())
            .execute()
    }

    suspend fun getAnimeYears(): ApolloResponse<AnimeYearsQuery.Data> {
        return client
            .query(AnimeYearsQuery())
            .execute()
    }

    suspend fun getAnimeSchedules(
        dayOfWeek: DayOfWeek
    ): ApolloResponse<AnimeSchedulesQuery.Data> {
        return client
            .query(
                AnimeSchedulesQuery(
                    dayOfWeek = dayOfWeek.toGraphQL()
                )
            )
            .execute()
    }
}