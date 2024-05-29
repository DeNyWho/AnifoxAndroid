package club.anifox.android.domain.usecase

import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeDetailUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(url: String): Flow<StateWrapper<AnimeDetail>> {
        return animeRepository.getAnimeDetail(url)
    }
}