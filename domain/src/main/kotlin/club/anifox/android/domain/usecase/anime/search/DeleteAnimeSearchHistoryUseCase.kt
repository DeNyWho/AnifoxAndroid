package club.anifox.android.domain.usecase.anime.search

import club.anifox.android.domain.repository.anime.AnimeRepository

class DeleteAnimeSearchHistoryUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke() = repository.deleteSearchHistory()
}