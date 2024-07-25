package club.anifox.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.usecase.anime.paging.GetAnimePagingUseCase
import club.anifox.android.feature.search.data.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getAnimePagingUseCase: GetAnimePagingUseCase,
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState(searchResults = emptyFlow()))
    val searchState = _searchState.asStateFlow()

    private var searchJob: Job? = null

    init {
        getSearchResults()
    }

    fun search(query: String) {
        searchJob?.cancel()
        _searchState.update { it.copy(searchResults = emptyFlow()) }
        searchJob = viewModelScope.launch {
            _searchState.update { it.copy(query = query) }
            delay(2000)
            getSearchResults()
        }
    }

    fun updateFilter(
        status: AnimeStatus? = null,
        type: AnimeType? = null,
        year: Int? = null,
        season: AnimeSeason? = null
    ) {
        _searchState.update {
            it.copy(
                status = status ?: it.status,
                type = type ?: it.type,
                year = year ?: it.year,
                season = season ?: it.season
            )
        }
        getSearchResults()
    }

    private fun getSearchResults() {
        val currentState = _searchState.value
        viewModelScope.launch {
            val pagingData = getAnimePagingUseCase(
                limit = 20,
                searchQuery = currentState.query,
                status = currentState.status,
                type = currentState.type,
                year = currentState.year,
                season = currentState.season
            ).cachedIn(viewModelScope)
            _searchState.update { it.copy(searchResults = pagingData) }
        }
    }
}