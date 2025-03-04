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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = _uiState.value,
    )

    private var searchDebouncerJob: Job = Job()
    private var currentQuery = ""
    private val ioDispatcher = Dispatchers.IO

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val _randomAnime = MutableStateFlow(StateListWrapper<AnimeLight>())
    val randomAnime = _randomAnime.asStateFlow()

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

    fun onQueryChanged(text: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(query = text)
            }
            search(text)
        }
    }

    fun search(query: String) {
        searchDebouncerJob.cancel()
        currentQuery = query

        if (query.isNotEmpty()) {
            _uiState.update { it.copy(pagingData = null) }

            searchDebouncerJob = viewModelScope.launch(ioDispatcher) {
                delay(timeMillis = 500)
                if (currentQuery != _uiState.value.lastSearchedQuery) {
                    addSearchHistoryUseCase.invoke(query)
                    _uiState.update { state ->
                        state.copy(lastSearchedQuery = query, pagingData = getPagingDataFlow(query))
                    }
                }
            }
        } else {
            _uiState.update { state ->
                state.copy(lastSearchedQuery = "", pagingData = null)
            }
        }
    }

    private fun getPagingDataFlow(query: String): Flow<PagingData<AnimeLight>> =
        animeSearchPagingUseCase.invoke(searchQuery = query).cachedIn(viewModelScope)


    fun clearSearch() {
        _uiState.update {
            SearchUiState(
                query = "",
                lastSearchedQuery = "",
                pagingData = null,
                error = null
            )
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            deleteSearchHistoryUseCase.invoke()
            _searchHistory.value = emptyList()
        }
    }
}