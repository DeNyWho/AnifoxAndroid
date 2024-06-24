package club.anifox.android.domain.usecase.anime

import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.repository.AnimeRepository
import club.anifox.android.domain.state.StateListWrapper
import kotlinx.coroutines.flow.Flow

class GetAnimeVideosUseCase(private val animeRepository: AnimeRepository) {
    operator fun invoke(
        url: String,
        videoType: VideoType?,
    ): Flow<StateListWrapper<AnimeVideosLight>> {
        return animeRepository.getAnimeVideos(url, videoType)
    }
}