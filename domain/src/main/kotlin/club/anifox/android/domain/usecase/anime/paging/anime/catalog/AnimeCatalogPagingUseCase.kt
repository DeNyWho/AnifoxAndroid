package club.anifox.android.domain.usecase.anime.paging.anime.catalog

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeCatalogPagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        limit: Int = 20,
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
    ): Flow<PagingData<AnimeLight>> {
        return animeRepository.getAnimeCatalogPaged(
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
            translation = translation,
            order = order,
            sort = sort,
        )
    }
}