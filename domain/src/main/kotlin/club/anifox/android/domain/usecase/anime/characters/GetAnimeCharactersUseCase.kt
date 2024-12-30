package club.anifox.android.domain.usecase.anime.characters

import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeCharactersUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        page: Int,
        limit: Int,
        url: String,
    ): Flow<StateListWrapper<AnimeCharactersLight>> {
        return animeRepository.getAnimeCharacters(
            page = page,
            limit = limit,
            url = url,
        )
    }
}