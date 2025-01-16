package club.anifox.android.domain.usecase.anime.local

import club.anifox.android.domain.repository.anime.AnimeLocalRepository
import kotlinx.coroutines.flow.Flow

class ObserveAnimeExistsUseCase(
    private val animeLocalRepository: AnimeLocalRepository
) {
    suspend operator fun invoke(url: String): Flow<Boolean> {
        return animeLocalRepository.observeAnimeExists(url)
    }
}