package club.anifox.android.domain.usecase.anime.paging.anime.genres

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeGenresPagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        limit: Int = 20,
        genre: String,
        minimalAge: Int? = null,
    ): Flow<PagingData<AnimeLight>> {
        return animeRepository.getAnimeGenresPaged(
            limit = limit,
            genre = genre,
            minimalAge = minimalAge,
        )
    }
}