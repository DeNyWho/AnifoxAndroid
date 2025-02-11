package club.anifox.android.feature.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.domain.usecase.settings.PlayerOrientationSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class PlayerViewModel @Inject constructor(
    private val playerOrientationSettingsUseCase: PlayerOrientationSettingsUseCase,
): ViewModel() {
    private val _selectedPlayerOrientation: MutableStateFlow<PlayerOrientation> =
        MutableStateFlow(PlayerOrientation.ALL)
    val selectedPlayerOrientation: StateFlow<PlayerOrientation> = _selectedPlayerOrientation.asStateFlow()

    init {
        viewModelScope.launch {
            launch { getPlayerOrientation() }
        }
    }

    private fun getPlayerOrientation() {
        playerOrientationSettingsUseCase.playerOrientation.onEach {
            _selectedPlayerOrientation.value = it
        }.launchIn(viewModelScope)
    }
}