package club.anifox.android.domain.usecase.anime.local

import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.repository.anime.AnimeLocalRepository

class InsertAnimeLocalUseCase(
    private val animeLocalRepository: AnimeLocalRepository,
) {
    suspend operator fun invoke(anime: AnimeDetail) {
        animeLocalRepository.insertAnimeDetail(anime)
    }
}