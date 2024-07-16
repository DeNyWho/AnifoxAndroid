package club.anifox.android.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType.Movie
import club.anifox.android.domain.model.anime.enum.FilterEnum
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val animeUseCase: GetAnimeUseCase,
) : ViewModel() {
    private val _onPopularOngoingAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val onPopularOngoingAnime: MutableState<StateListWrapper<AnimeLight>> = _onPopularOngoingAnime

    private val _onPopularAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val onPopularAnime: MutableState<StateListWrapper<AnimeLight>> = _onPopularAnime

    private val _filmsAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val filmsAnime: MutableState<StateListWrapper<AnimeLight>> = _filmsAnime

    fun getPopularOngoingAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit, filter = FilterEnum.ShikimoriRating, status = AnimeStatus.Ongoing).onEach {
            _onPopularOngoingAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getPopularAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit, filter = FilterEnum.ShikimoriRating).onEach {
            _onPopularAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getFilmsAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit, type = Movie).onEach {
            _filmsAnime.value = it
        }.launchIn(viewModelScope)
    }
}