package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeStudiosUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(): Flow<StateListWrapper<AnimeStudio>> {
        return animeRepository.getAnimeStudios()
    }
}