package club.anifox.android.domain.usecase.anime.paging.anime.characters

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeCharactersPagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        limit: Int = 24,
        url: String,
        search: String,
    ): Flow<PagingData<AnimeCharactersLight>> {
        return animeRepository.getAnimeCharactersPaged(
            limit = limit,
            url = url,
            search = search,
        )
    }
}