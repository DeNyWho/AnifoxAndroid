package club.anifox.android.feature.translations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeTranslationsCountUseCase
import club.anifox.android.feature.translations.model.state.TranslationsUiState
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
internal class TranslationsViewModel @Inject constructor(
    private val translationsCountUseCase: GetAnimeTranslationsCountUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(TranslationsUiState())
    val uiState: StateFlow<TranslationsUiState> = _uiState.asStateFlow()

    private val _animeTranslationsCount = MutableStateFlow<StateListWrapper<AnimeTranslationsCount>>(StateListWrapper.loading())
    val animeTranslationsCount = _animeTranslationsCount.asStateFlow()

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
        getTranslationsCount(url)
    }

    private fun getTranslationsCount(url: String) {
        translationsCountUseCase.invoke(url).onEach {
            _animeTranslationsCount.value = it
        }.launchIn(viewModelScope)
    }

}