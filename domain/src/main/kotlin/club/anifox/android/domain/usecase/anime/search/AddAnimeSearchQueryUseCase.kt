package club.anifox.android.domain.usecase.anime.search

import club.anifox.android.domain.repository.anime.AnimeRepository

class AddAnimeSearchQueryUseCase(private val repository: AnimeRepository) {
    suspend operator fun invoke(query: String) {
        if (query.isNotBlank()) {
            repository.addSearchHistory(query.trim())
        }
    }
}