package club.anifox.android.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.model.anime.enum.AnimeSort
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType.Movie
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _animeOfSeason: MutableStateFlow<StateListWrapper<AnimeLight>> =
        MutableStateFlow(StateListWrapper())
    val animeOfSeason: StateFlow<StateListWrapper<AnimeLight>> =
        _animeOfSeason.asStateFlow()

    private val _onPopularAnime: MutableStateFlow<StateListWrapper<AnimeLight>> =
        MutableStateFlow(StateListWrapper())
    val onPopularAnime: StateFlow<StateListWrapper<AnimeLight>> =
        _onPopularAnime.asStateFlow()

    private val _onUpdatedAnime: MutableStateFlow<StateListWrapper<AnimeLight>> =
        MutableStateFlow(StateListWrapper())
    val onUpdatedAnime: StateFlow<StateListWrapper<AnimeLight>> =
        _onUpdatedAnime.asStateFlow()

    private val _filmsAnime: MutableStateFlow<StateListWrapper<AnimeLight>> =
        MutableStateFlow(StateListWrapper())
    val filmsAnime: StateFlow<StateListWrapper<AnimeLight>> =
        _filmsAnime.asStateFlow()

    private val _genresAnime: MutableStateFlow<StateListWrapper<AnimeGenre>> =
        MutableStateFlow(StateListWrapper())
    val genresAnime: StateFlow<StateListWrapper<AnimeGenre>> =
        _genresAnime.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
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

    private fun getAnimeOfSeason() {
        animeUseCase.invoke(
            page = DEFAULT_PAGE,
            limit = DEFAULT_LIMIT,
            status = AnimeStatus.Ongoing,
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
            status = AnimeStatus.Ongoing,
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