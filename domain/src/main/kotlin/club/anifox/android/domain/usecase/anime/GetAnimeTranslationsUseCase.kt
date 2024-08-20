package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.repository.anime.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeTranslationsUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(): Flow<StateListWrapper<AnimeTranslation>> {
        return animeRepository.getAnimeTranslations()
    }
}