package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeDetailUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        url: String
    ): Flow<StateWrapper<AnimeDetail>> {
        return animeRepository.getAnimeDetail(url)
    }
}