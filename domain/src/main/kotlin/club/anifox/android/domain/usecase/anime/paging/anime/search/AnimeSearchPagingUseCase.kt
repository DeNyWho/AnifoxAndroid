package club.anifox.android.domain.usecase.anime.paging.anime.search

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeSearchPagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        limit: Int = 20,
        searchQuery: String? = null,
    ): Flow<PagingData<AnimeLight>> {
        return animeRepository.getAnimeSearchPaged(
            limit = limit,
            searchQuery = searchQuery,
        )
    }
}