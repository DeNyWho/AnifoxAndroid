package club.anifox.android.feature.character

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.character.full.CharacterFull
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.domain.usecase.character.GetCharacterFullUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
internal class CharacterViewModel @Inject constructor(
    private val getCharacterFullUseCase: GetCharacterFullUseCase,
): ViewModel() {
    private val _character: MutableState<StateWrapper<CharacterFull>> =
        mutableStateOf(StateWrapper.loading())
    val character: MutableState<StateWrapper<CharacterFull>> = _character

    fun loadData(id: String) {
        getCharacterFull(id)
    }

    private fun getCharacterFull(id: String) {
        getCharacterFullUseCase.invoke(id).onEach {
            _character.value = it
        }.launchIn(viewModelScope)
    }

}