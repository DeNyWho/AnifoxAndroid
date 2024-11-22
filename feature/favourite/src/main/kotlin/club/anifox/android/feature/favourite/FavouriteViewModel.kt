package club.anifox.android.feature.favourite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.usecase.anime.GetFavouriteAnimeUseCase
import club.anifox.android.feature.favourite.model.state.FavouriteState
import club.anifox.android.feature.favourite.model.tab.FavouriteTabType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class FavouriteViewModel @Inject constructor(
    private val getFavouriteAnimeUseCase: GetFavouriteAnimeUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(FavouriteState())
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            state.value.tabs.forEach { tab ->
                launch {
                    getFavouriteAnimeUseCase(tab.toAnimeListType())
                        .catch { e ->
                            _state.update { it.copy(error = e.message) }
                        }
                        .collect { items ->
                            _state.update { currentState ->
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
        _state.update { it.copy(currentTab = tab) }
    }


}