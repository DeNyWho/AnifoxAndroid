package club.anifox.android.feature.screenshots

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeScreenshotUseCase
import club.anifox.android.feature.screenshots.model.state.ScreenshotsUiState
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
internal class ScreenshotsViewModel @Inject constructor(
    private val animeScreenshotUseCase: GetAnimeScreenshotUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ScreenshotsUiState> =
        MutableStateFlow(ScreenshotsUiState())
    val uiState: StateFlow<ScreenshotsUiState> =
        _uiState.asStateFlow()

    private val _screenshotsAnime: MutableStateFlow<StateListWrapper<String>> =
        MutableStateFlow(StateListWrapper())
    val screenshotsAnime: StateFlow<StateListWrapper<String>> =
        _screenshotsAnime.asStateFlow()

    fun initialize(url: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isInitialized = true,
                )
            }

            loadData(url)
        }
    }

    private fun loadData(url: String) {
        getScreenshotAnime(url)
    }

    private fun getScreenshotAnime(url: String) {
        animeScreenshotUseCase.invoke(url = url).onEach {
            _screenshotsAnime.value = it
        }.launchIn(viewModelScope)
    }
}