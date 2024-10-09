package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        page: Int = 0,
        limit: Int = 12,
        status: AnimeStatus? = null,
        genres: List<String>? = null,
        searchQuery: String? = null,
        season: AnimeSeason? = null,
        ratingMpa: String? = null,
        minimalAge: Int? = null,
        type: AnimeType? = null,
        years: List<Int>? = null,
        studios: List<String>? = null,
        order: AnimeOrder? = null,
        sort: AnimeSort? = null,
    ): Flow<StateListWrapper<AnimeLight>> {
        return animeRepository.getAnime(
            page = page,
            limit = limit,
            status = status,
            genres = genres,
            searchQuery = searchQuery,
            season = season,
            ratingMpa = ratingMpa,
            minimalAge = minimalAge,
            type = type,
            years = years,
            studios = studios,
            order = order,
            sort = sort,
        )
    }
}