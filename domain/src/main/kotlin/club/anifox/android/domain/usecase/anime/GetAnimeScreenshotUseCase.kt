package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeScreenshotUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        url: String,
        limit: Int? = null,
    ): Flow<StateListWrapper<String>> {
        return animeRepository.getAnimeScreenshots(url, limit)
    }
}