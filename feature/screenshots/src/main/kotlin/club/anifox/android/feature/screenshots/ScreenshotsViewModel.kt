package club.anifox.android.feature.screenshots

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeScreenshotUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class ScreenshotsViewModel @Inject constructor(
    private val animeScreenshotUseCase: GetAnimeScreenshotUseCase,
): ViewModel() {
    private val _screenshotsAnime: MutableState<StateListWrapper<String>> =
        mutableStateOf(StateListWrapper())
    val screenshotsAnime: MutableState<StateListWrapper<String>> = _screenshotsAnime

    fun getScreenshotAnime(url: String) {
        animeScreenshotUseCase.invoke(url = url).onEach {
            _screenshotsAnime.value = it
        }.launchIn(viewModelScope)
    }

}