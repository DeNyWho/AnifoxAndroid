package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeTranslationsCountUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(url: String): Flow<StateListWrapper<AnimeTranslationsCount>> {
        return animeRepository.getAnimeTranslationsCount(url)
    }
}