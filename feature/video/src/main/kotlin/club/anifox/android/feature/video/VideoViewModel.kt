package club.anifox.android.feature.video

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.core.common.util.deeplink.DeepLink
import club.anifox.android.domain.model.anime.enum.VideoType
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeVideosUseCase
import club.anifox.android.feature.video.model.state.VideoUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class VideoViewModel @Inject constructor(
    private val animeVideosUseCase: GetAnimeVideosUseCase,
    private val deepLink: DeepLink,
) : ViewModel() {
    private val _uiState: MutableStateFlow<VideoUiState> =
        MutableStateFlow(VideoUiState())
    val uiState: StateFlow<VideoUiState> = _uiState.asStateFlow()

    private val _trailerVideos: MutableStateFlow<StateListWrapper<AnimeVideosLight>> =
        MutableStateFlow(StateListWrapper())
    val trailerVideos: StateFlow<StateListWrapper<AnimeVideosLight>> =
        _trailerVideos.asStateFlow()

    private val _openingVideos: MutableStateFlow<StateListWrapper<AnimeVideosLight>> =
        MutableStateFlow(StateListWrapper())
    val openingVideos: StateFlow<StateListWrapper<AnimeVideosLight>> =
        _openingVideos.asStateFlow()

    private val _endingVideos: MutableStateFlow<StateListWrapper<AnimeVideosLight>> =
        MutableStateFlow(StateListWrapper())
    val endingVideos: StateFlow<StateListWrapper<AnimeVideosLight>> =
        _endingVideos.asStateFlow()

    private val _otherVideos: MutableStateFlow<StateListWrapper<AnimeVideosLight>> =
        MutableStateFlow(StateListWrapper())
    val otherVideos: StateFlow<StateListWrapper<AnimeVideosLight>> =
        _otherVideos.asStateFlow()

    fun initialize(url: String, animeTitle: String?) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    url = url,
                    animeTitle = animeTitle,
                    isInitialized = true,
                )
            }

            loadData(url)
        }
    }

    private fun loadData(url: String) {
        getTrailerVideos(url)
        getOpeningVideos(url)
        getEndingVideos(url)
        getOtherVideos(url)
    }

    private fun getTrailerVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Trailer, null).onEach {
            _trailerVideos.value = it
        }.launchIn(viewModelScope)
    }

    private fun getOpeningVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Opening, null).onEach {
            _openingVideos.value = it
        }.launchIn(viewModelScope)
    }

    private fun getEndingVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Ending, null).onEach {
            _endingVideos.value = it
        }.launchIn(viewModelScope)
    }

    private fun getOtherVideos(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = VideoType.Other, null).onEach {
            _otherVideos.value = it
        }.launchIn(viewModelScope)
    }

    fun openYoutube(youtubeUrl: String) {
        deepLink.openYouTubeApp(youtubeUrl)
    }
}