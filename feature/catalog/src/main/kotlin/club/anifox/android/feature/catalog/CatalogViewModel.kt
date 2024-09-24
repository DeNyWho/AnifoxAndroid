package club.anifox.android.feature.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.AnimePagingUseCase
import club.anifox.android.feature.catalog.data.CatalogState
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
internal class CatalogViewModel @Inject constructor(
    private val getAnimeGenresUseCase: GetAnimeGenresUseCase,
    private val animePagingUseCase: AnimePagingUseCase,
): ViewModel() {
    private val _catalogState = MutableStateFlow(CatalogState())
    val catalogState = _catalogState.asStateFlow()

    val loadState = MutableStateFlow<CombinedLoadStates?>(null)

    private val _animeGenres = MutableStateFlow<StateListWrapper<AnimeGenre>>(StateListWrapper.loading())
    val animeGenres = _animeGenres.asStateFlow()

    init {
        viewModelScope.launch {
            getAnimeGenresUseCase().collect { _animeGenres.value = it }
        }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _catalogState.update { it.copy(isLoading = isLoading) }
    }

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _catalogState
        .onStart { _catalogState.update { it.copy(isLoading = true) } }
        .debounce(0)
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animePagingUseCase(
                limit = 20,
                genres = listOf(state.genre),
                minimalAge = state.minimalAge,
                filter = state.filter,
            )
        }


}