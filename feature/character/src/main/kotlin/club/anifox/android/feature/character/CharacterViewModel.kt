package club.anifox.android.feature.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.domain.usecase.character.GetCharacterFullUseCase
import club.anifox.android.feature.character.model.state.CharacterUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    private val getCharacterFullUseCase: GetCharacterFullUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<CharacterUiState> =
        MutableStateFlow(CharacterUiState())
    val uiState: StateFlow<CharacterUiState> =
        _uiState.asStateFlow()

    private val _character: MutableStateFlow<StateWrapper<CharacterFull>> =
        MutableStateFlow(StateWrapper.loading())
    val character: StateFlow<StateWrapper<CharacterFull>> =
        _character.asStateFlow()

    fun initialize(id: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    id = id,
                    isInitialized = true,
                )
            }

            loadData(id)
        }
    }

    private fun loadData(id: String) {
        getCharacterFull(id)
    }

    private fun getCharacterFull(id: String) {
        getCharacterFullUseCase.invoke(id).onEach {
            _character.value = it
        }.launchIn(viewModelScope)
    }
}