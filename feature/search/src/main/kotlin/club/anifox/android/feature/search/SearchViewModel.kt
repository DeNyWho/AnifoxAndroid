package club.anifox.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.search.AnimeSearchPagingUseCase
import club.anifox.android.domain.usecase.anime.search.AddAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.anime.search.DeleteAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.anime.search.GetAnimeSearchHistoryUseCase
import club.anifox.android.feature.search.data.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val animeUseCase: GetAnimeUseCase,
    private val animeSearchPagingUseCase: AnimeSearchPagingUseCase,
    private val getSearchHistoryUseCase: GetAnimeSearchHistoryUseCase,
    private val addSearchHistoryUseCase: AddAnimeSearchHistoryUseCase,
    private val deleteSearchHistoryUseCase: DeleteAnimeSearchHistoryUseCase,
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState(isInitial = true))
    val searchState = _searchState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val _randomAnime = MutableStateFlow(StateListWrapper<AnimeLight>())
    val randomAnime = _randomAnime.asStateFlow()

    val loadState = MutableStateFlow<CombinedLoadStates?>(null)

    private var previousQuery = ""

    init {
        viewModelScope.launch {
            getSearchHistoryUseCase.invoke()
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5000L),
                    initialValue = emptyList()
                )
                .collect { history ->
                    _searchHistory.value = history
                }
        }

        viewModelScope.launch {
            animeUseCase.invoke(
                page = 0,
                limit = 1,
                order = AnimeOrder.Random
            ).collect { anime ->
                _randomAnime.value = anime
            }
        }
    }

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _searchState
        .onStart { _searchState.update { it.copy(isLoading = true) } }
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { state ->
            if (state.query.isNotBlank() &&
                state.query.length > previousQuery.length &&
                state.query != previousQuery
            ) {
                viewModelScope.launch {
                    addSearchHistoryUseCase.invoke(state.query)
                }
                // Set isInitial to false when user starts searching
                _searchState.update { it.copy(isInitial = false) }
            }
            previousQuery = state.query

            if (state.query.isBlank()) {
                flow { emit(PagingData.empty()) }
            } else {
                animeSearchPagingUseCase.invoke(
                    limit = 20,
                    searchQuery = state.query,
                )
            }
        }

    fun updateLoadingState(isLoading: Boolean) {
        _searchState.update { it.copy(isLoading = isLoading) }
    }

    fun updateLoadState(loadState: CombinedLoadStates) {
        this.loadState.value = loadState

        if (loadState.refresh is LoadState.NotLoading) {
            updateLoadingState(false)
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            if (query != searchState.value.query) {
                _searchState.update {
                    it.copy(
                        query = query,
                        isLoading = true,
                        isInitial = false // Set to false when user searches
                    )
                }
            } else {
                _searchState.update { it.copy(query = query) }
            }
        }
    }

    fun deleteSearchHistory() {
        viewModelScope.launch {
            deleteSearchHistoryUseCase.invoke()
        }
    }
}