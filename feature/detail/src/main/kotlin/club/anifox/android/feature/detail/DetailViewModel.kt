package club.anifox.android.feature.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.domain.usecase.GetAnimeDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val animeDetailUseCase: GetAnimeDetailUseCase,
) : ViewModel() {
    private val _detailAnime: MutableState<StateWrapper<AnimeDetail>> =
        mutableStateOf(StateWrapper())
    val detailAnime: MutableState<StateWrapper<AnimeDetail>> = _detailAnime

    fun getDetailAnime(url: String) {
        animeDetailUseCase.invoke(url = url).onEach {
            _detailAnime.value = it
        }.launchIn(viewModelScope)
    }
}