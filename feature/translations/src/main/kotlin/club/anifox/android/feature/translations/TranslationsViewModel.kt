package club.anifox.android.feature.translations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeTranslationsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class TranslationsViewModel @Inject constructor(
    private val translationsCountUseCase: GetAnimeTranslationsCountUseCase,
): ViewModel() {
    private val _animeTranslationsCount = MutableStateFlow<StateListWrapper<AnimeTranslationsCount>>(StateListWrapper.loading())
    val animeTranslationsCount = _animeTranslationsCount.asStateFlow()

    fun getTranslationsCount(url: String) {
        translationsCountUseCase.invoke(url).onEach {
            _animeTranslationsCount.value = it
        }.launchIn(viewModelScope)
    }

}