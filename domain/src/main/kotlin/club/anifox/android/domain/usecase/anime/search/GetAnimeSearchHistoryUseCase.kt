package club.anifox.android.domain.usecase.anime.search

import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class GetAnimeSearchHistoryUseCase(private val repository: AnimeRepository) {
    operator fun invoke(): Flow<List<String>> = repository.getLastSearchesHistory()
}