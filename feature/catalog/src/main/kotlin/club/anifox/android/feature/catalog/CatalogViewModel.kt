package club.anifox.android.feature.catalog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.CombinedLoadStates
import androidx.paging.PagingData
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
    private val animeCatalogPagingUseCase: AnimeCatalogPagingUseCase,
    private val getAnimeYearsUseCase: GetAnimeYearsUseCase,
    private val getAnimeStudiosUseCase: GetAnimeStudiosUseCase,
    private val getAnimeTranslationsUseCase: GetAnimeTranslationsUseCase,
): ViewModel() {
    private val _catalogState = MutableStateFlow(CatalogState())
    val catalogState = _catalogState.asStateFlow()

    val loadState = MutableStateFlow<CombinedLoadStates?>(null)

    private val _animeGenres = MutableStateFlow<StateListWrapper<AnimeGenre>>(StateListWrapper.loading())
    val animeGenres = _animeGenres.asStateFlow()

    private val _animeYears = MutableStateFlow<StateListWrapper<Int>>(StateListWrapper.loading())
    val animeYears = _animeYears.asStateFlow()

    private val _animeStudios = MutableStateFlow<StateListWrapper<AnimeStudio>>(StateListWrapper.loading())
    val animeStudios = _animeStudios.asStateFlow()

    private val _animeTranslations = MutableStateFlow<StateListWrapper<AnimeTranslation>>(StateListWrapper.loading())
    val animeTranslations = _animeTranslations.asStateFlow()

    init {
        viewModelScope.launch {
            getAnimeGenresUseCase().collect { _animeGenres.value = it }
        }
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
        _catalogState.update { it.copy(isLoading = isLoading) }
    }

    @OptIn(FlowPreview::class)
    val searchResults: Flow<PagingData<AnimeLight>> = _catalogState
        .onStart { _catalogState.update { it.copy(isLoading = true) } }
        .debounce(0)
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animeCatalogPagingUseCase(
                limit = 20,
                genres = state.genres,
                minimalAge = state.minimalAge,
                filter = state.filter,
                status = state.status,
                type = state.type,
                year = state.year,
                season = state.season,
            )
        }

    fun initializeParams(initialParams: CatalogFilterParams) {
        viewModelScope.launch {
            if (!_catalogState.value.isInitialized) {
                _catalogState.update { state ->
                    state.copy(
                        isInitialized = true,
                        genres = initialParams.genres,
                        status = initialParams.status,
                        type = initialParams.type,
                        year = initialParams.year,
                        season = initialParams.season,
                        studio = initialParams.studio,
                        filter = initialParams.filter,
                    )
                }
            }
        }
    }

    fun updateLoadState(loadState: CombinedLoadStates) {
        this.loadState.value = loadState
    }
}