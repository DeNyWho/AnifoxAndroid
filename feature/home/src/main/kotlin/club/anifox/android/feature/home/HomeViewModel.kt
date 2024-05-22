package club.anifox.android.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val animeUseCase: GetAnimeUseCase,
) : ViewModel() {
    private val _onPopularOngoingAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val onPopularOngoingAnime: MutableState<StateListWrapper<AnimeLight>> = _onPopularOngoingAnime

    fun getPopularOngoingAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit, filter = FilterEnum.ShikimoriRating, status = AnimeStatus.Ongoing).onEach {
            _onPopularOngoingAnime.value = it
        }.launchIn(viewModelScope)
    }
}