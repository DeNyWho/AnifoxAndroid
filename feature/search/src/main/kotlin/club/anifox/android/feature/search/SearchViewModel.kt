package club.anifox.android.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeStudiosUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeTranslationsUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeYearsUseCase
import club.anifox.android.domain.usecase.anime.paging.GetAnimePagingUseCase
import club.anifox.android.feature.search.data.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SearchViewModel @Inject constructor(
    private val getAnimePagingUseCase: GetAnimePagingUseCase,
    private val getAnimeYearsUseCase: GetAnimeYearsUseCase,
    private val getAnimeStudiosUseCase: GetAnimeStudiosUseCase,
    private val getAnimeTranslationsUseCase: GetAnimeTranslationsUseCase,
) : ViewModel() {
    private val _searchState = MutableStateFlow(SearchState())
    val searchState = _searchState.asStateFlow()

    val loadState = MutableStateFlow<CombinedLoadStates?>(null)

    private val _animeYears = MutableStateFlow<StateListWrapper<Int>>(StateListWrapper.loading())
    val animeYears = _animeYears.asStateFlow()

    private val _animeStudios = MutableStateFlow<StateListWrapper<AnimeStudio>>(StateListWrapper.loading())
    val animeStudios = _animeStudios.asStateFlow()

    private val _animeTranslations = MutableStateFlow<StateListWrapper<AnimeTranslation>>(StateListWrapper.loading())
    val animeTranslations = _animeTranslations.asStateFlow()

    init {
        viewModelScope.launch {
            getAnimeYearsUseCase().collect { _animeYears.value = it }
        }
        viewModelScope.launch {
            getAnimeStudiosUseCase().collect { _animeStudios.value = it }
        }
        viewModelScope.launch {
            getAnimeTranslationsUseCase().collect { _animeTranslations.value = it }
        }
    }

    fun updateLoadingState(isLoading: Boolean) {
        _searchState.update { it.copy(isLoading = isLoading) }
    }

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _searchState
        .onStart { _searchState.update { it.copy(isLoading = true) } }
        .debounce(0)
        .distinctUntilChanged()
        .flatMapLatest { state ->
            getAnimePagingUseCase(
                limit = 20,
                searchQuery = state.query,
                status = state.status,
                type = state.type,
                year = state.year,
                season = state.season,
                studio = state.studio?.name,
                translation = null,
            )
        }

    fun updateLoadState(loadState: CombinedLoadStates) {
        this.loadState.value = loadState
    }

    fun search(query: String) {
        _searchState.update { it.copy(query = query, isLoading = true) }
    }

    fun updateFilter(
        status: AnimeStatus? = null,
        type: AnimeType? = null,
        year: Int? = null,
        season: AnimeSeason? = null,
        studio: AnimeStudio? = null,
        translation: AnimeTranslation? = null,
    ) {
        _searchState.update {
            it.copy(
                status = status ?: it.status,
                type = type ?: it.type,
                year = year ?: it.year,
                season = season ?: it.season,
                studio = studio ?: it.studio,
                translation = translation ?: it.translation,
            )
        }
    }
}