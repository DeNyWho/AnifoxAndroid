package club.anifox.android.feature.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.usecase.anime.favourite.GetFavouriteAnimeUseCase
import club.anifox.android.feature.favourite.model.state.FavouriteUiState
import club.anifox.android.feature.favourite.model.tab.FavouriteTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouriteViewModel @Inject constructor(
    private val getFavouriteAnimeUseCase: GetFavouriteAnimeUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState.asStateFlow()

    private val favouriteCache =
        mutableMapOf<FavouriteTabType, Flow<PagingData<AnimeLightFavourite>>>()

    private val loadingStateMap = MutableStateFlow<Map<FavouriteTabType, Boolean>>(emptyMap())

    init {
        if (_uiState.value.tabs.isEmpty()) {
            _uiState.update { it.copy(tabs = FavouriteTabType.getAllEntries()) }
        }
    }

    fun getFavouritesForTab(tab: FavouriteTabType): Flow<PagingData<AnimeLightFavourite>> {
        return favouriteCache.getOrPut(tab) {
            getFavouriteAnimeUseCase(tab.toAnimeListType())
                .onStart {
                    updateLoadingState(tab, true)
                }
                .onCompletion {
                    updateLoadingState(tab, false)
                }
                .catch { e ->
                    _uiState.update { currentState ->
                        currentState.copy(error = e.message)
                    }
                }
        }
    }

    fun prefetchFavouritesForTab(tab: FavouriteTabType) {
        viewModelScope.launch {
            if (!favouriteCache.containsKey(tab)) {
                favouriteCache[tab] = getFavouriteAnimeUseCase(tab.toAnimeListType())
                    .onStart {
                        updateLoadingState(tab, true)
                    }
                    .onCompletion {
                        updateLoadingState(tab, false)
                    }
            }
        }
    }

    private fun updateLoadingState(tab: FavouriteTabType, isLoading: Boolean) {
        loadingStateMap.update { currentState ->
            currentState.toMutableMap().apply {
                this[tab] = isLoading
            }
        }
        _uiState.update {
            it.copy(loading = loadingStateMap.value.values.any { loading -> loading })
        }
    }
}