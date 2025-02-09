package club.anifox.android.feature.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType.Movie
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val animeUseCase: GetAnimeUseCase,
    private val animeGenresUseCase: GetAnimeGenresUseCase,
) : ViewModel() {

    private val _animeOfSeason: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val animeOfSeason: MutableState<StateListWrapper<AnimeLight>> = _animeOfSeason

    private val _onPopularAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val onPopularAnime: MutableState<StateListWrapper<AnimeLight>> = _onPopularAnime

    private val _onUpdatedAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val onUpdatedAnime: MutableState<StateListWrapper<AnimeLight>> = _onUpdatedAnime

    private val _filmsAnime: MutableState<StateListWrapper<AnimeLight>> =
        mutableStateOf(StateListWrapper())
    val filmsAnime: MutableState<StateListWrapper<AnimeLight>> = _filmsAnime

    private val _genresAnime: MutableState<StateListWrapper<AnimeGenre>> =
        mutableStateOf(StateListWrapper())
    val genresAnime: MutableState<StateListWrapper<AnimeGenre>> = _genresAnime

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    getAnimeOfSeason()
                }
                launch {
                    getPopularAnime()
                }
                launch {
                    getUpdatedAnime()
                }
                launch {
                    getAnimeGenres()
                }
                launch {
                    getFilmsAnime()
                }
            }
        }
    }

    private fun getAnimeOfSeason() {
        animeUseCase.invoke(
            page = DEFAULT_PAGE,
            limit = DEFAULT_LIMIT,
            status = AnimeStatus.Ongoing,
            season = AnimeSeason.fromMonth(LocalDate.now().month.value),
            years = listOf(LocalDate.now().year),
            order = AnimeOrder.Rating,
            sort = AnimeSort.Desc,
        ).onEach {
            _animeOfSeason.value = it
        }.launchIn(viewModelScope)
    }

    private fun getAnimeGenres() {
        animeGenresUseCase.invoke().onEach {
            _genresAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getPopularAnime() {
        animeUseCase.invoke(
            page = DEFAULT_PAGE,
            limit = DEFAULT_LIMIT,
            order = AnimeOrder.Rating,
            sort = AnimeSort.Desc,
        ).onEach {
            _onPopularAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getUpdatedAnime() {
        animeUseCase.invoke(
            page = DEFAULT_PAGE,
            limit = DEFAULT_LIMIT,
            order = AnimeOrder.Update,
            sort = AnimeSort.Desc,
        ).onEach {
            _onUpdatedAnime.value = it
        }.launchIn(viewModelScope)
    }

    private fun getFilmsAnime() {
        animeUseCase.invoke(
            page = DEFAULT_PAGE,
            limit = DEFAULT_LIMIT,
            type = Movie,
        ).onEach {
            _filmsAnime.value = it
        }.launchIn(viewModelScope)
    }

    private companion object {
        private const val DEFAULT_PAGE = 0
        private const val DEFAULT_LIMIT = 12
    }
}