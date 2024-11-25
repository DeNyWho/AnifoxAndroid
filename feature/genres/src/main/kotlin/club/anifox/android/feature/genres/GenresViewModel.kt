package club.anifox.android.feature.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.genres.AnimeGenresPagingUseCase
import club.anifox.android.feature.genres.data.GenreUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class GenresViewModel @Inject constructor(
    private val getAnimeGenresUseCase: GetAnimeGenresUseCase,
    private val animeGenresPagingUseCase: AnimeGenresPagingUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

    val searchResults: Flow<PagingData<AnimeLight>> = _uiState
        .filter { it.isReadyToLoad }
        .distinctUntilChanged { old, new ->
            old.selectedGenre == new.selectedGenre &&
                    old.minimalAge == new.minimalAge
        }
        .flatMapLatest { state ->
            animeGenresPagingUseCase.invoke(
                limit = 20,
                genre = state.selectedGenre.id,
                minimalAge = state.minimalAge,
            )
        }
        .cachedIn(viewModelScope)

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            getAnimeGenresUseCase.invoke().collect { genresResult ->
                val genres = genresResult.data

                _uiState.update { currentState ->
                    currentState.copy(
                        genres = genres,
                        isLoading = false,
                        isGenresLoaded = genres.isNotEmpty(),
                    )
                }
            }
        }
    }

    fun initializeFilter(genreId: String, minimalAge: Int? = null) {
        viewModelScope.launch {
            // Setting the download status before filtering starts
            _uiState.update { it.copy(isContentLoading = true) }
            val genre = _uiState.value.genres
                .find { it.id == genreId } ?: return@launch

            _uiState.update { currentState ->
                currentState.copy(
                    selectedGenre = genre,
                    minimalAge = minimalAge,
                    isReadyToLoad = true,
                    // delete the download status only after updating the data
                    isContentLoading = false,
                )
            }
        }
    }
}