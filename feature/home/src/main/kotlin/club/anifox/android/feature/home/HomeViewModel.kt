package club.anifox.android.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType.Movie
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val animeUseCase: GetAnimeUseCase,
    private val animeGenresUseCase: GetAnimeGenresUseCase,
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

    private val _genresAnime: MutableState<StateListWrapper<AnimeGenre>> =
        mutableStateOf(StateListWrapper())
    val genresAnime: MutableState<StateListWrapper<AnimeGenre>> = _genresAnime

    fun getPopularOngoingAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit, status = AnimeStatus.Ongoing).onEach {
            _onPopularOngoingAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getAnimeGenres() {
        animeGenresUseCase.invoke().onEach {
            _genresAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getPopularAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit).onEach {
            _onPopularAnime.value = it
        }.launchIn(viewModelScope)
    }

    fun getFilmsAnime(page: Int, limit: Int) {
        animeUseCase.invoke(page = page, limit = limit, type = Movie).onEach {
            _filmsAnime.value = it
        }.launchIn(viewModelScope)
    }
}