package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeYearsUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(): Flow<StateListWrapper<Int>> {
        return animeRepository.getAnimeYears()
    }
}