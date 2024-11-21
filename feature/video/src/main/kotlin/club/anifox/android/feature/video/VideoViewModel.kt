package club.anifox.android.feature.video

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.core.common.util.deeplink.DeepLink
import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class VideoViewModel @Inject constructor(
    private val animeVideosUseCase: GetAnimeVideosUseCase,
    private val deepLink: DeepLink,
): ViewModel() {
    private val _trailerVideos: MutableState<StateListWrapper<AnimeVideosLight>> =
        mutableStateOf(StateListWrapper())
    val trailerVideos: MutableState<StateListWrapper<AnimeVideosLight>> = _trailerVideos

    private val _openingVideos: MutableState<StateListWrapper<AnimeVideosLight>> =
        mutableStateOf(StateListWrapper())
    val openingVideos: MutableState<StateListWrapper<AnimeVideosLight>> = _openingVideos

    private val _endingVideos: MutableState<StateListWrapper<AnimeVideosLight>> =
        mutableStateOf(StateListWrapper())
    val endingVideos: MutableState<StateListWrapper<AnimeVideosLight>> = _endingVideos

    private val _otherVideos: MutableState<StateListWrapper<AnimeVideosLight>> =
        mutableStateOf(StateListWrapper())
    val otherVideos: MutableState<StateListWrapper<AnimeVideosLight>> = _otherVideos

    fun getTrailerVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Trailer, null).onEach {
            _trailerVideos.value = it
        }.launchIn(viewModelScope)
    }

    fun getOpeningVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Opening, null).onEach {
            _openingVideos.value = it
        }.launchIn(viewModelScope)
    }

    fun getEndingVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Ending, null).onEach {
            _endingVideos.value = it
        }.launchIn(viewModelScope)
    }

    fun getOtherVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Other, null).onEach {
            _otherVideos.value = it
        }.launchIn(viewModelScope)
    }

    fun openYoutube(youtubeUrl: String) {
        deepLink.openYouTubeApp(youtubeUrl)
    }
}