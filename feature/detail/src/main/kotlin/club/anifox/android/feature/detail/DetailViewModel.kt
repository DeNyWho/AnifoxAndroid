package club.anifox.android.feature.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.core.common.util.deeplink.DeepLink
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeDetailUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeRelatedUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeScreenshotUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeSimilarUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val animeDetailUseCase: GetAnimeDetailUseCase,
    private val animeSimilarUseCase: GetAnimeSimilarUseCase,
    private val animeRelatedUseCase: GetAnimeRelatedUseCase,
    private val animeScreenshotUseCase: GetAnimeScreenshotUseCase,
    private val animeVideosUseCase: GetAnimeVideosUseCase,
    private val deepLink: DeepLink,
) : ViewModel() {
    private val _detailAnime: MutableState<StateWrapper<AnimeDetail>> =
        mutableStateOf(StateWrapper())
    val detailAnime: MutableState<StateWrapper<AnimeDetail>> = _detailAnime

    private val _similarAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val similarAnime: MutableState<StateListWrapper<AnimeLight>> = _similarAnime

    private val _relatedAnime: MutableState<StateListWrapper<AnimeRelatedLight>> =
        mutableStateOf(StateListWrapper())
    val relatedAnime: MutableState<StateListWrapper<AnimeRelatedLight>> = _relatedAnime

    private val _screenshotsAnime: MutableState<StateListWrapper<String>> =
        mutableStateOf(StateListWrapper())
    val screenshotsAnime: MutableState<StateListWrapper<String>> = _screenshotsAnime

    private val _videosAnime: MutableState<StateListWrapper<AnimeVideosLight>> =
        mutableStateOf(StateListWrapper())
    val videosAnime: MutableState<StateListWrapper<AnimeVideosLight>> = _videosAnime

    fun getDetailAnime(url: String) {
        animeDetailUseCase.invoke(url = url).onEach {
            _detailAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getSimilarAnime(url: String) {
        animeSimilarUseCase.invoke(url = url).onEach {
            _similarAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getRelatedAnime(url: String) {
        animeRelatedUseCase.invoke(url = url).onEach {
            _relatedAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getScreenshotAnime(url: String) {
        animeScreenshotUseCase.invoke(url = url, limit = 10).onEach {
            _screenshotsAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getVideosAnime(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = null, 5).onEach {
            _videosAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun openYoutube(youtubeUrl: String) {
        deepLink.openYouTubeApp(youtubeUrl)
    }
}