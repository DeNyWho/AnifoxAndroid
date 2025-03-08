package club.anifox.android.feature.detail

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
import club.anifox.android.feature.detail.model.state.DetailUiState
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
    private val _uiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> =
        _uiState.asStateFlow()

    private val _detailAnime: MutableStateFlow<StateWrapper<AnimeDetail>> =
        MutableStateFlow(StateWrapper.loading())
    val detailAnime: StateFlow<StateWrapper<AnimeDetail>> =
        _detailAnime.asStateFlow()

    private val _similarAnime: MutableStateFlow<StateListWrapper<AnimeLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val similarAnime: StateFlow<StateListWrapper<AnimeLight>> =
        _similarAnime.asStateFlow()

    private val _relatedAnime: MutableStateFlow<StateListWrapper<AnimeRelatedLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val relatedAnime: StateFlow<StateListWrapper<AnimeRelatedLight>> =
        _relatedAnime.asStateFlow()

    private val _screenshotsAnime: MutableStateFlow<StateListWrapper<String>> =
        MutableStateFlow(StateListWrapper.loading())
    val screenshotsAnime: StateFlow<StateListWrapper<String>> =
        _screenshotsAnime.asStateFlow()

    private val _videosAnime: MutableStateFlow<StateListWrapper<AnimeVideosLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val videosAnime: StateFlow<StateListWrapper<AnimeVideosLight>> =
        _videosAnime.asStateFlow()

    private val _charactersAnime: MutableStateFlow<StateListWrapper<AnimeCharactersLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val charactersAnime: StateFlow<StateListWrapper<AnimeCharactersLight>> =
        _charactersAnime.asStateFlow()

    private val _selectedFavouriteStatus: MutableStateFlow<AnimeFavouriteStatus?> =
        MutableStateFlow(null)
    val selectedFavouriteStatus: StateFlow<AnimeFavouriteStatus?> =
        _selectedFavouriteStatus.asStateFlow()

    private val _isInFavourite: MutableStateFlow<Boolean> =
        MutableStateFlow(false)
    val isInFavourite: StateFlow<Boolean> =
        _isInFavourite.asStateFlow()

    fun initialize(url: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    url = url,
                    isInitialized = true,
                )
            }

            loadData(url)
        }
    }

    private fun loadData(url: String) {
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
        animeCharactersUseCase.invoke(page = DEFAULT_PAGE, limit = DEFAULT_LIMIT, url = url)
            .onEach {
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
        viewModelScope.launch {
            _selectedFavouriteStatus.value = status
            val url = _uiState.value.url

            if (url.isNotEmpty()) {
                val animeDetail = _detailAnime.value.data
                if (animeDetail != null) {
                    val animeExists = checkAnimeLocalUseCase.invoke(url)

                    if (!animeExists) {
                        insertAnimeLocalUseCase(animeDetail)
                    }

                    observeAnimeExistsUseCase.invoke(url)
                        .collect { exists ->
                            if (exists) {
                                updateAnimeUseCase.updateAnimeStatus(
                                    url = url,
                                    status = status
                                )
                            }
                        }
                }
            }
        }
    }

    fun updateIsInFavourite() {
        viewModelScope.launch {
            _isInFavourite.value = !_isInFavourite.value
            val url = _uiState.value.url

            if (url.isNotEmpty()) {
                val animeDetail = _detailAnime.value.data
                if (animeDetail != null) {
                    val animeExists = checkAnimeLocalUseCase.invoke(url)

                    if (!animeExists) {
                        insertAnimeLocalUseCase(animeDetail)
                    }

                    observeAnimeExistsUseCase.invoke(url)
                        .collect { exists ->
                            if (exists) {
                                updateAnimeUseCase.updateAnimeFavourite(
                                    url = url,
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