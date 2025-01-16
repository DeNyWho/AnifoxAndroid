package club.anifox.android.feature.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.usecase.anime.favourite.GetFavouriteAnimeUseCase
import club.anifox.android.feature.favourite.model.state.FavouriteUiState
import club.anifox.android.feature.favourite.model.tab.FavouriteTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouriteViewModel @Inject constructor(
    private val getFavouriteAnimeUseCase: GetFavouriteAnimeUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavouriteUiState())
    val uiState: StateFlow<FavouriteUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            uiState.value.tabs.forEach { tab ->
                launch {
                    getFavouriteAnimeUseCase(tab.toAnimeListType())
                        .catch { e ->
                            _uiState.update { it.copy(error = e.message) }
                        }
                        .collect { items ->
                            _uiState.update { currentState ->
                                currentState.copy(
                                    items = currentState.items + (tab to items),
                                    loading = false
                                )
                            }
                        }
                }
            }
        }
    }

    fun onTabSelected(tab: FavouriteTabType) {
        _uiState.update { it.copy(currentTab = tab) }
    }


}