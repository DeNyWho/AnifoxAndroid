package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeSimilarUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        page: Int,
        limit: Int,
        url: String,
    ): Flow<StateListWrapper<AnimeLight>> {
        return animeRepository.getAnimeSimilar(page, limit, url)
    }
}