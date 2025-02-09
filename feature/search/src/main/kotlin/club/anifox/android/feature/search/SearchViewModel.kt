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
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
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

    private val _searchQuery = MutableStateFlow("")

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
    val searchResults: Flow<PagingData<AnimeLight>> = _searchQuery
        .onStart {
            _uiState.update {
                it.copy(
                    isLoading = true,
                )
            }
        }
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { query ->
            when {
                query.isNotBlank() -> {
                    viewModelScope.launch { addSearchHistoryUseCase.invoke(query) }
                    animeSearchPagingUseCase.invoke(limit = 20, searchQuery = query)
                }
                else -> {
                    flow { emit(PagingData.empty()) }
                }
            }
        }
        .onEach {
            _uiState.update {
                it.copy(
                    isLoading = false,
                )
            }
        }
        .cachedIn(viewModelScope)

    fun search(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    query = query,
                    isLoading = true,
                )
            }
            _searchQuery.emit(query)
        }
    }

    fun clearSearch() {
        viewModelScope.launch {
            _searchQuery.emit("")
            _uiState.update {
                it.copy(
                    query = "",
                    isLoading = false,
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