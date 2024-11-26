package club.anifox.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
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
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val _randomAnime = MutableStateFlow(StateListWrapper<AnimeLight>())
    val randomAnime = _randomAnime.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            coroutineScope {
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
    }

    private suspend fun loadRandomAnime() {
        _randomAnime.value = StateListWrapper.loading()
        delay(1000)
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

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _uiState
        .debounce(500)
        .distinctUntilChanged { old, new -> old.query == new.query }
        .flatMapLatest { state ->
            _uiState.update { it.copy(isWaiting = false) }
            when {
                state.query.isNotBlank() && state.query != state.previousQuery -> {
                    viewModelScope.launch { addSearchHistoryUseCase.invoke(state.query) }
                    _uiState.update {
                        it.copy(isInitialized = false, previousQuery = state.query)
                    }
                    animeSearchPagingUseCase.invoke(limit = 20, searchQuery = state.query)
                }
                state.query.isBlank() -> flow { emit(PagingData.empty()) }
                else -> animeSearchPagingUseCase.invoke(limit = 20, searchQuery = state.query)
            }
        }

    fun search(query: String) {
        viewModelScope.launch {
            if (query != uiState.value.query) {
                _uiState.update {
                    it.copy(
                        query = query,
                        isInitialized = false,
                        isWaiting = true,
                    )
                }
            } else {
                _uiState.update { it.copy(query = query) }
            }
        }
    }

    fun clearSearch() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    query = "",
                )
            }
        }
    }

    fun deleteSearchHistory() {
        viewModelScope.launch {
            deleteSearchHistoryUseCase.invoke()
            _searchHistory.value = emptyList()
            _uiState.update {
                it.copy(
                    isInitialized = true,
                )
            }
        }
    }
}