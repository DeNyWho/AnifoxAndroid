package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeSimilarUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        url: String,
    ): Flow<StateListWrapper<AnimeLight>> {
        return animeRepository.getAnimeSimilar(url)
    }
}