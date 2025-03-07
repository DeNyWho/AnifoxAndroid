package club.anifox.android.feature.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.domain.usecase.anime.paging.anime.characters.AnimeCharactersPagingUseCase
import club.anifox.android.feature.characters.model.state.CharactersUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
internal class CharactersViewModel @Inject constructor(
    private val animeCharactersPagingUseCase: AnimeCharactersPagingUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(CharactersUiState())
    val uiState: StateFlow<CharactersUiState> = _uiState.asStateFlow()

    val charactersResults: Flow<PagingData<AnimeCharactersLight>> = _uiState
        .filter { it.isInitialized }
        .distinctUntilChanged()
        .flatMapLatest { state ->
            animeCharactersPagingUseCase(
                url = state.url,
                role = state.selectedRole,
            )
        }
        .cachedIn(viewModelScope)

    fun updateFilter(
        role: String?,
    ) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRole = role,
                url = currentState.url,
                isInitialized = currentState.isInitialized,
                isLoading = currentState.isLoading,
            )
        }
    }

    fun initializeParams(initialUrl: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    url = initialUrl,
                    selectedRole = null,
                    isInitialized = true,
                )
            }
        }
    }
}