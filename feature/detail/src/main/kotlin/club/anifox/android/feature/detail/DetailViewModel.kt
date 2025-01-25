package club.anifox.android.feature.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.core.common.util.deeplink.DeepLink
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.model.anime.enum.AnimeFavouriteStatus
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeDetailUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeRelatedUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeScreenshotUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeSimilarUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeVideosUseCase
import club.anifox.android.domain.usecase.anime.characters.GetAnimeCharactersUseCase
import club.anifox.android.domain.usecase.anime.favourite.CheckAnimeFavouriteUseCase
import club.anifox.android.domain.usecase.anime.favourite.UpdateAnimeUseCase
import club.anifox.android.domain.usecase.anime.local.CheckAnimeLocalUseCase
import club.anifox.android.domain.usecase.anime.local.InsertAnimeLocalUseCase
import club.anifox.android.domain.usecase.anime.local.ObserveAnimeExistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class DetailViewModel @Inject constructor(
    private val animeDetailUseCase: GetAnimeDetailUseCase,
    private val animeSimilarUseCase: GetAnimeSimilarUseCase,
    private val animeRelatedUseCase: GetAnimeRelatedUseCase,
    private val animeScreenshotUseCase: GetAnimeScreenshotUseCase,
    private val animeVideosUseCase: GetAnimeVideosUseCase,
    private val animeCharactersUseCase: GetAnimeCharactersUseCase,
    private val updateAnimeUseCase: UpdateAnimeUseCase,
    private val checkAnimeFavouriteUseCase: CheckAnimeFavouriteUseCase,
    private val observeAnimeExistsUseCase: ObserveAnimeExistsUseCase,
    private val insertAnimeLocalUseCase: InsertAnimeLocalUseCase,
    private val checkAnimeLocalUseCase: CheckAnimeLocalUseCase,
    private val deepLink: DeepLink,
) : ViewModel() {
    private val _detailAnime: MutableState<StateWrapper<AnimeDetail>> =
        mutableStateOf(StateWrapper.loading())
    val detailAnime: MutableState<StateWrapper<AnimeDetail>> = _detailAnime

    private val _similarAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper.loading())
    val similarAnime: MutableState<StateListWrapper<AnimeLight>> = _similarAnime

    private val _relatedAnime: MutableState<StateListWrapper<AnimeRelatedLight>> =
        mutableStateOf(StateListWrapper.loading())
    val relatedAnime: MutableState<StateListWrapper<AnimeRelatedLight>> = _relatedAnime

    private val _screenshotsAnime: MutableState<StateListWrapper<String>> =
        mutableStateOf(StateListWrapper.loading())
    val screenshotsAnime: MutableState<StateListWrapper<String>> = _screenshotsAnime

    private val _videosAnime: MutableState<StateListWrapper<AnimeVideosLight>> =
        mutableStateOf(StateListWrapper.loading())
    val videosAnime: MutableState<StateListWrapper<AnimeVideosLight>> = _videosAnime

    private val _charactersAnime: MutableState<StateListWrapper<AnimeCharactersLight>> =
        mutableStateOf(StateListWrapper.loading())
    val charactersAnime: MutableState<StateListWrapper<AnimeCharactersLight>> = _charactersAnime

    private val _selectedFavouriteStatus = mutableStateOf<AnimeFavouriteStatus?>(null)
    val selectedFavouriteStatus: MutableState<AnimeFavouriteStatus?> = _selectedFavouriteStatus

    private val _isInFavourite: MutableState<Boolean> = mutableStateOf(false)
    val isInFavourite: MutableState<Boolean> = _isInFavourite

    private val _currentUrl: MutableState<String> = mutableStateOf("")

    fun loadData(url: String) {
        _currentUrl.value = url
        getDetailAnime(url)
        getScreenshotAnime(url)
        getVideosAnime(url)
        getRelatedAnime(url)
        getSimilarAnime(url)
        getCharactersAnime(url)
        checkFavouriteAnime(url)
    }

    private fun checkFavouriteAnime(url: String) {
        viewModelScope.launch {
            _isInFavourite.value = checkAnimeFavouriteUseCase.isAnimeInFavourite(url)
            _selectedFavouriteStatus.value = checkAnimeFavouriteUseCase.getAnimeStatus(url)
        }
    }

    private fun getCharactersAnime(url: String) {
        animeCharactersUseCase.invoke(page = DEFAULT_PAGE, limit = DEFAULT_LIMIT, url = url).onEach {
            _charactersAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getDetailAnime(url: String) {
        animeDetailUseCase.invoke(url = url).onEach {
            _detailAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getSimilarAnime(url: String) {
        animeSimilarUseCase.invoke(page = DEFAULT_PAGE, limit = DEFAULT_LIMIT, url = url).onEach {
            _similarAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getRelatedAnime(url: String) {
        animeRelatedUseCase.invoke(url = url).onEach {
            _relatedAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getScreenshotAnime(url: String) {
        animeScreenshotUseCase.invoke(url = url, limit = DEFAULT_SCREENSHOT_LIMIT).onEach {
            _screenshotsAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getVideosAnime(url: String) {
        animeVideosUseCase.invoke(url = url, videoType = null, DEFAULT_VIDEO_LIMIT).onEach {
            _videosAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun updateFavouriteStatus(status: AnimeFavouriteStatus?) {
        _selectedFavouriteStatus.value = status
        viewModelScope.launch {
            if (_currentUrl.value.isNotEmpty()) {
                val animeDetail = _detailAnime.value.data
                if (animeDetail != null) {
                    val animeExists = checkAnimeLocalUseCase.invoke(_currentUrl.value)

                    if (!animeExists) {
                        insertAnimeLocalUseCase(animeDetail)
                    }

                    observeAnimeExistsUseCase.invoke(_currentUrl.value)
                        .collect { exists ->
                            if (exists) {
                                updateAnimeUseCase.updateAnimeStatus(
                                    url = _currentUrl.value,
                                    status = status
                                )
                            }
                        }
                }
            }
        }
    }

    fun updateIsInFavourite() {
        _isInFavourite.value = !_isInFavourite.value
        viewModelScope.launch {
            if (_currentUrl.value.isNotEmpty()) {
                val animeDetail = _detailAnime.value.data
                if (animeDetail != null) {
                    val animeExists = checkAnimeLocalUseCase.invoke(_currentUrl.value)

                    if (!animeExists) {
                        insertAnimeLocalUseCase(animeDetail)
                    }

                    observeAnimeExistsUseCase.invoke(_currentUrl.value)
                        .collect { exists ->
                            if (exists) {
                                updateAnimeUseCase.updateAnimeFavourite(
                                    url = _currentUrl.value,
                                    isFavourite = _isInFavourite.value
                                )
                            }
                        }
                }
            }
        }
    }

    fun openYoutube(youtubeUrl: String) {
        deepLink.openYouTubeApp(youtubeUrl)
    }

    private companion object {
        private const val DEFAULT_PAGE = 0
        private const val DEFAULT_LIMIT = 12
        private const val DEFAULT_SCREENSHOT_LIMIT = 10
        private const val DEFAULT_VIDEO_LIMIT = 5
    }
}