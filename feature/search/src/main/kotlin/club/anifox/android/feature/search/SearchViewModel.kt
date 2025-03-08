package club.anifox.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeOrder
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.search.AnimeSearchPagingUseCase
import club.anifox.android.domain.usecase.anime.search.AddAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.anime.search.DeleteAnimeSearchHistoryUseCase
import club.anifox.android.domain.usecase.anime.search.GetAnimeSearchHistoryUseCase
import club.anifox.android.feature.search.model.state.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach
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
    private val _uiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> =
        _uiState.asStateFlow()

    private val _searchHistory: MutableStateFlow<List<String>> =
        MutableStateFlow(emptyList())
    val searchHistory: StateFlow<List<String>> =
        _searchHistory.asStateFlow()

    private val _randomAnime: MutableStateFlow<StateListWrapper<AnimeLight>> =
        MutableStateFlow(StateListWrapper.loading())
    val randomAnime: StateFlow<StateListWrapper<AnimeLight>> =
        _randomAnime.asStateFlow()

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _uiState
        .debounce(500)
        .distinctUntilChanged { old, new -> old.query == new.query }
        .flatMapLatest { state ->
            if (state.query.isNotEmpty()) {
                addSearchHistoryUseCase.invoke(state.query)
                animeSearchPagingUseCase.invoke(
                    limit = 20,
                    searchQuery = state.query,
                )
            } else {
                MutableStateFlow(PagingData.empty())
            }
        }
        .onEach {
            _uiState.update { it.copy(hasSearched = true) }
        }
        .cachedIn(viewModelScope)

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            launch {
                getSearchHistoryUseCase.invoke().collect {
                    _searchHistory.value = it
                }
            }
            launch {
                loadRandomAnime()
            }
        }
    }

    private suspend fun loadRandomAnime() {
        _randomAnime.value = StateListWrapper.loading()
        animeUseCase.invoke(
            page = 0,
            limit = 1,
            order = AnimeOrder.Random
        ).collect { anime ->
            _randomAnime.value = anime
        }
    }

    fun refreshRandomAnime() {
        viewModelScope.launch {
            loadRandomAnime()
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    query = query,
                    hasSearched = false,
                )
            }
        }
    }

    fun updateSearchBarState() {
        _uiState.update {
            it.copy(isSearchBarFocused = true)
        }
    }

    fun clearSearch() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    query = "",
                    hasSearched = false,
                )
            }
        }
    }

    fun deleteSearchHistory() {
        viewModelScope.launch {
            deleteSearchHistoryUseCase.invoke()
            _searchHistory.value = emptyList()
        }
    }
}