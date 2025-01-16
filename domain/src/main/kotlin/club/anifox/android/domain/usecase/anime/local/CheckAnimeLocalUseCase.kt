package club.anifox.android.domain.usecase.anime.local

import club.anifox.android.domain.repository.anime.AnimeLocalRepository

class CheckAnimeLocalUseCase(
    private val animeLocalRepository: AnimeLocalRepository,
) {
    suspend operator fun invoke(url: String): Boolean {
        return animeLocalRepository.checkAnimeUrl(url)
    }
}