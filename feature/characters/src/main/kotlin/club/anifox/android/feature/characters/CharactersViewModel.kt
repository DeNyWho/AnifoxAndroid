package club.anifox.android.feature.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.usecase.anime.paging.anime.characters.AnimeCharactersPagingUseCase
import club.anifox.android.feature.characters.model.state.CharactersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharactersViewModel @Inject constructor(
    private val animeCharactersPagingUseCase: AnimeCharactersPagingUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CharactersUiState> =
        MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> =
        _uiState.asStateFlow()

    @OptIn(FlowPreview::class)
    val charactersResults: Flow<PagingData<AnimeCharactersLight>> = _uiState
        .filter { it.isInitialized }
        .debounce(500)
        .distinctUntilChanged { old, new -> old.searchQuery == new.searchQuery }
        .flatMapLatest { state ->
            animeCharactersPagingUseCase.invoke(
                url = state.url,
                search = state.searchQuery,
            )
        }
        .cachedIn(viewModelScope)

    fun search(query: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchQuery = query,
                )
            }
        }
    }

    fun clearSearch() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchQuery = "",
                )
            }
        }
    }

    fun initializeParams(initialUrl: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    url = initialUrl,
                    searchQuery = "",
                    isInitialized = true,
                )
            }
        }
    }
}