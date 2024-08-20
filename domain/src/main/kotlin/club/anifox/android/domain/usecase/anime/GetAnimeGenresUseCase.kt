package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeGenresUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(): Flow<StateListWrapper<AnimeGenre>> {
        return animeRepository.getAnimeGenres()
    }
}