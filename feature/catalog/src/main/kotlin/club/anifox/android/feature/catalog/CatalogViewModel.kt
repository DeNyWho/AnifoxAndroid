package club.anifox.android.feature.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.anime.studio.AnimeStudio
import club.anifox.android.domain.model.anime.translations.AnimeTranslation
import club.anifox.android.domain.model.navigation.catalog.CatalogFilterParams
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.domain.usecase.anime.GetAnimeGenresUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeStudiosUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeTranslationsUseCase
import club.anifox.android.domain.usecase.anime.GetAnimeYearsUseCase
import club.anifox.android.domain.usecase.anime.paging.anime.catalog.AnimeCatalogPagingUseCase
import club.anifox.android.feature.catalog.model.FilterType
import club.anifox.android.feature.catalog.model.state.CatalogUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
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
internal class CatalogViewModel @Inject constructor(
    private val getAnimeGenresUseCase: GetAnimeGenresUseCase,
    private val animeCatalogPagingUseCase: AnimeCatalogPagingUseCase,
    private val getAnimeYearsUseCase: GetAnimeYearsUseCase,
    private val getAnimeStudiosUseCase: GetAnimeStudiosUseCase,
    private val getAnimeTranslationsUseCase: GetAnimeTranslationsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CatalogUiState())
    val uiState: StateFlow<CatalogUiState> = _uiState.asStateFlow()

    private val _animeGenres =
        MutableStateFlow<StateListWrapper<AnimeGenre>>(StateListWrapper.loading())
    val animeGenres = _animeGenres.asStateFlow()

    private val _animeYears = MutableStateFlow<StateListWrapper<Int>>(StateListWrapper.loading())
    val animeYears = _animeYears.asStateFlow()

    private val _animeStudios =
        MutableStateFlow<StateListWrapper<AnimeStudio>>(StateListWrapper.loading())
    val animeStudios = _animeStudios.asStateFlow()

    private val _animeTranslations =
        MutableStateFlow<StateListWrapper<AnimeTranslation>>(StateListWrapper.loading())
    val animeTranslations = _animeTranslations.asStateFlow()

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            coroutineScope {
                launch {
                    getAnimeGenresUseCase.invoke().collect {
                        _animeGenres.value = it
                    }
                }
                launch {
                    getAnimeYearsUseCase.invoke().collect {
                        _animeYears.value = it
                    }
                }
                launch {
                    getAnimeStudiosUseCase.invoke().collect {
                        _animeStudios.value = it
                    }
                }
                launch {
                    getAnimeTranslationsUseCase.invoke().collect {
                        _animeTranslations.value = it
                    }
                }
            }
        }
    }

    val searchResults: Flow<PagingData<AnimeLight>> = _uiState
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animeCatalogPagingUseCase(
                limit = 20,
                genres = state.selectedGenres?.map { it.id },
                studios = state.selectedStudios?.map { it.id },
                minimalAge = state.selectedMinimalAge,
                status = state.selectedStatus,
                type = state.selectedType,
                years = state.selectedYears,
                season = state.selectedSeason,
                order = state.selectedOrder,
                sort = state.selectedSort,
            )
        }
        .cachedIn(viewModelScope)

    fun updateFilter(
        filterParams: CatalogFilterParams,
        filterType: FilterType,
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedGenres = if (filterType == FilterType.GENRE) filterParams.genres else currentState.selectedGenres,
                selectedStatus = if (filterType == FilterType.STATUS) filterParams.status else currentState.selectedStatus,
                selectedType = if (filterType == FilterType.TYPE) filterParams.type else currentState.selectedType,
                selectedYears = if (filterType == FilterType.YEARS) filterParams.years else currentState.selectedYears,
                selectedSeason = if (filterType == FilterType.SEASON) filterParams.season else currentState.selectedSeason,
                selectedStudios = if (filterType == FilterType.STUDIO) filterParams.studios else currentState.selectedStudios,
                selectedTranslation = if (filterType == FilterType.TRANSLATION) filterParams.translation else currentState.selectedTranslation,
                selectedOrder = if (filterType == FilterType.ORDER) filterParams.order else currentState.selectedOrder,
                selectedSort = if (filterType == FilterType.SORT) filterParams.sort else currentState.selectedSort,
                selectedMinimalAge = currentState.selectedMinimalAge,
                isInitialized = currentState.isInitialized,
                isLoading = currentState.isLoading,
            )
        }
    }

    fun initializeFilters(initialParams: CatalogFilterParams) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    selectedGenres = initialParams.genres,
                    selectedStatus = initialParams.status,
                    selectedType = initialParams.type,
                    selectedYears = initialParams.years,
                    selectedSeason = initialParams.season,
                    selectedStudios = initialParams.studios,
                    selectedTranslation = initialParams.translation,
                    selectedOrder = initialParams.order,
                    selectedSort = initialParams.sort,
                    isInitialized = true,
                )
            }
        }
    }
}