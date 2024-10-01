package club.anifox.android.domain.usecase.anime.paging.anime.catalog

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.enum.FilterEnum
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
        year: Int? = null,
        studio: String? = null,
        translation: List<Int>? = null,
        filter: FilterEnum? = null,
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
            year = year,
            studio = studio,
            translation = translation,
            filter = filter,
        )
    }
}