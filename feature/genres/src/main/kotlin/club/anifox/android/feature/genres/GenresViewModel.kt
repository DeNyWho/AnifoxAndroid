package club.anifox.android.feature.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.genres.AnimeGenresPagingUseCase
import club.anifox.android.feature.genres.data.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GenresViewModel @Inject constructor(
    private val getAnimeGenresUseCase: GetAnimeGenresUseCase,
    private val animeGenresPagingUseCase: AnimeGenresPagingUseCase,
): ViewModel() {
    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    val loadState = MutableStateFlow<CombinedLoadStates?>(null)

    private val _animeGenres = MutableStateFlow<StateListWrapper<AnimeGenre>>(StateListWrapper.loading())
    val animeGenres = _animeGenres.asStateFlow()

    init {
        viewModelScope.launch {
            getAnimeGenresUseCase().collect { _animeGenres.value = it }
        }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _searchState.update { it.copy(isLoading = isLoading) }
    }

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _searchState
        .onStart { _searchState.update { it.copy(isLoading = true) } }
        .debounce(0)
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animeGenresPagingUseCase(
                limit = 20,
                genre = state.genre,
                minimalAge = state.minimalAge,
                filter = state.filter,
            )
        }

    fun updateLoadState(loadState: CombinedLoadStates) {
        this.loadState.value = loadState
    }

    fun initializeFilter(genre: String) {
        if (!_searchState.value.isInitialized) {
            _searchState.update {
                it.copy(
                    genre = genre,
                    minimalAge = it.minimalAge,
                    filter = it.filter,
                    isInitialized = true,
                    isLoading = it.isLoading,
                )
            }
        }
    }

    fun updateFilter(genre: String) {
        _searchState.update {
            it.copy(
                genre = genre,
                minimalAge = it.minimalAge,
                filter = it.filter,
            )
        }
    }
}