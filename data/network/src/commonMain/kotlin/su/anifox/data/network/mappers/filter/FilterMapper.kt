package su.anifox.data.network.mappers.filter

import com.apollographql.apollo.api.Optional
import su.anifox.domain.model.anime.enum.AnimeOrder
import su.anifox.domain.model.anime.enum.AnimeSeason
import su.anifox.domain.model.anime.enum.AnimeSort
import su.anifox.domain.model.anime.enum.AnimeStatus
import su.anifox.domain.model.anime.enum.AnimeType
import su.anifox.domain.model.anime.enum.RatingMpa
import su.anifox.domain.model.anime.enum.character.CharacterRole
import su.anifox.domain.model.anime.filter.AnimeFilters
import su.anifox.domain.model.anime.filter.CharacterFilters
import su.anifox.domain.model.common.enum.DayOfWeek

internal object FilterMapper {
    fun AnimeFilters.toGraphQLGenres(): Optional<List<String>> {
        return genres?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLStudios(): Optional<List<String>> {
        return studios?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLYears(): Optional<List<Int>> {
        return years?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLTranslations(): Optional<List<Int>> {
        return translations?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLSearch(): Optional<String> {
        return search?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLSeason(): Optional<su.anifox.graphql.type.AnimeSeason> {
        return season?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLStatus(): Optional<su.anifox.graphql.type.AnimeStatus> {
        return status?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLType(): Optional<su.anifox.graphql.type.AnimeType> {
        return type?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLRatingMpa(): Optional<su.anifox.graphql.type.RatingMpa> {
        return ratingMpa?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLMinimalAge(): Optional<Int> {
        return minimalAge?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLOrder(): Optional<su.anifox.graphql.type.AnimeOrder> {
        return order?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun AnimeFilters.toGraphQLSort(): Optional<su.anifox.graphql.type.SortOrder> {
        return sort?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun CharacterFilters.toGraphQLAnimeUrl(): Optional<String> {
        return animeUrl?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun CharacterFilters.toGraphQLSearch(): Optional<String> {
        return search?.let { Optional.present(it) } ?: Optional.absent()
    }

    fun CharacterFilters.toGraphQLRole(): Optional<su.anifox.graphql.type.CharacterRole> {
        return role?.let {
            Optional.present(it.toGraphQL())
        } ?: Optional.absent()
    }

    fun DayOfWeek.toGraphQL(): su.anifox.graphql.type.DayOfWeek {
        return when(this) {
            DayOfWeek.MONDAY -> su.anifox.graphql.type.DayOfWeek.MONDAY
            DayOfWeek.TUESDAY -> su.anifox.graphql.type.DayOfWeek.TUESDAY
            DayOfWeek.WEDNESDAY -> su.anifox.graphql.type.DayOfWeek.WEDNESDAY
            DayOfWeek.THURSDAY -> su.anifox.graphql.type.DayOfWeek.THURSDAY
            DayOfWeek.FRIDAY -> su.anifox.graphql.type.DayOfWeek.FRIDAY
            DayOfWeek.SATURDAY -> su.anifox.graphql.type.DayOfWeek.SATURDAY
            DayOfWeek.SUNDAY -> su.anifox.graphql.type.DayOfWeek.SUNDAY
        }
    }

    private fun AnimeSeason.toGraphQL(): su.anifox.graphql.type.AnimeSeason {
        return when (this) {
            AnimeSeason.WINTER -> su.anifox.graphql.type.AnimeSeason.WINTER
            AnimeSeason.SPRING -> su.anifox.graphql.type.AnimeSeason.SPRING
            AnimeSeason.SUMMER -> su.anifox.graphql.type.AnimeSeason.SUMMER
            AnimeSeason.FALL -> su.anifox.graphql.type.AnimeSeason.FALL
        }
    }

    private fun AnimeStatus.toGraphQL(): su.anifox.graphql.type.AnimeStatus {
        return when (this) {
            AnimeStatus.ANNOUNCED -> su.anifox.graphql.type.AnimeStatus.ANNOUNCED
            AnimeStatus.ONGOING -> su.anifox.graphql.type.AnimeStatus.ONGOING
            AnimeStatus.COMPLETED -> su.anifox.graphql.type.AnimeStatus.COMPLETED
        }
    }

    private fun AnimeType.toGraphQL(): su.anifox.graphql.type.AnimeType {
        return when (this) {
            AnimeType.TV -> su.anifox.graphql.type.AnimeType.TV
            AnimeType.MOVIE -> su.anifox.graphql.type.AnimeType.MOVIE
            AnimeType.OVA -> su.anifox.graphql.type.AnimeType.OVA
            AnimeType.ONA -> su.anifox.graphql.type.AnimeType.ONA
            AnimeType.SPECIAL -> su.anifox.graphql.type.AnimeType.SPECIAL
            AnimeType.MUSIC -> su.anifox.graphql.type.AnimeType.MUSIC
        }
    }

    private fun RatingMpa.toGraphQL(): su.anifox.graphql.type.RatingMpa {
        return when (this) {
            RatingMpa.G -> su.anifox.graphql.type.RatingMpa.G
            RatingMpa.PG -> su.anifox.graphql.type.RatingMpa.PG
            RatingMpa.PG_13 -> su.anifox.graphql.type.RatingMpa.PG_13
            RatingMpa.R -> su.anifox.graphql.type.RatingMpa.R
            RatingMpa.R_PLUS -> su.anifox.graphql.type.RatingMpa.R_PLUS
        }
    }

    private fun AnimeOrder.toGraphQL(): su.anifox.graphql.type.AnimeOrder {
        return when (this) {
            AnimeOrder.RATING -> su.anifox.graphql.type.AnimeOrder.RATING
            AnimeOrder.RELEASED_ON -> su.anifox.graphql.type.AnimeOrder.RELEASED_ON
            AnimeOrder.AIRED_ON -> su.anifox.graphql.type.AnimeOrder.AIRED_ON
            AnimeOrder.UPDATED_AT -> su.anifox.graphql.type.AnimeOrder.UPDATED_AT
            AnimeOrder.RANDOM -> su.anifox.graphql.type.AnimeOrder.RANDOM
        }
    }

    private fun AnimeSort.toGraphQL(): su.anifox.graphql.type.SortOrder {
        return when (this) {
            AnimeSort.ASC -> su.anifox.graphql.type.SortOrder.ASC
            AnimeSort.DESC -> su.anifox.graphql.type.SortOrder.DESC
        }
    }

    private fun CharacterRole.toGraphQL(): su.anifox.graphql.type.CharacterRole {
        return when (this) {
            CharacterRole.MAIN -> su.anifox.graphql.type.CharacterRole.MAIN
            CharacterRole.SUPPORTING -> su.anifox.graphql.type.CharacterRole.SUPPORTING
        }
    }
}