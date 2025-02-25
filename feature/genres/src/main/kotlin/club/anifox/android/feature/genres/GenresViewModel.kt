package club.anifox.android.feature.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.usecase.anime.paging.anime.genres.AnimeGenresPagingUseCase
import club.anifox.android.feature.genres.model.state.GenreUiState
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
    private val animeGenresPagingUseCase: AnimeGenresPagingUseCase,
): ViewModel() {
    private val _uiState = MutableStateFlow(GenreUiState())
    val uiState: StateFlow<GenreUiState> = _uiState.asStateFlow()

    val searchResults: Flow<PagingData<AnimeLight>> = _uiState
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animeGenresPagingUseCase.invoke(
                limit = 20,
                genre = state.selectedGenre.id,
                minimalAge = state.minimalAge,
            )
        }
        .cachedIn(viewModelScope)

    fun initializeFilter(genre: AnimeGenre) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedGenre = genre,
                    minimalAge = null,
                    isInitialized = true,
                )
            }
        }
    }
}