package club.anifox.android.domain.usecase.anime.search

import club.anifox.android.domain.repository.anime.AnimeRepository

class DeleteAnimeSearchQueryUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String) = repository.deleteSearchHistory(query)
}