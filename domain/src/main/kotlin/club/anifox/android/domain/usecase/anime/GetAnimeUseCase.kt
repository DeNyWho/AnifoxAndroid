package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.repository.AnimeRepository
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
        minimalAge: String? = null,
        type: AnimeType? = null,
        year: Int? = null,
        studio: String? = null,
        filter: FilterEnum? = null,
    ): Flow<StateListWrapper<AnimeLight>> {
        return animeRepository.getAnime(
            page, limit, status, genres, searchQuery, season, ratingMpa,
            minimalAge, type, year, studio, filter
        )
    }
}