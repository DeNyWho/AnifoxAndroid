package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeRelatedUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        url: String,
    ): Flow<StateListWrapper<AnimeRelatedLight>> {
        return animeRepository.getAnimeRelated(url)
    }
}