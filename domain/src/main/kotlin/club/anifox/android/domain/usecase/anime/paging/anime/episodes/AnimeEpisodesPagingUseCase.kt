package club.anifox.android.domain.usecase.anime.paging.anime.episodes

import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.repository.anime.AnimeRepository
import kotlinx.coroutines.flow.Flow

class AnimeEpisodesPagingUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        limit: Int = 24,
        url: String,
        translationId: Int,
        sort: AnimeSort,
        search: String,
    ): Flow<PagingData<AnimeEpisodesLight>> {
        return animeRepository.getAnimeEpisodesPaged(
            limit = limit,
            url = url,
            translationId = translationId,
            sort = sort,
            search = search,
        )
    }
}