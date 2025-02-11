package club.anifox.android.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.domain.model.common.device.ThemeType
import club.anifox.android.domain.usecase.settings.PlayerOrientationSettingsUseCase
import club.anifox.android.domain.usecase.settings.ThemeSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    private val themeSettingsUseCase: ThemeSettingsUseCase,
    private val playerOrientationSettingsUseCase: PlayerOrientationSettingsUseCase,
): ViewModel() {
    private val _selectedTheme: MutableStateFlow<ThemeType> =
        MutableStateFlow(ThemeType.SYSTEM)
    val selectedTheme: StateFlow<ThemeType> = _selectedTheme.asStateFlow()

    private val _selectedPlayerOrientation: MutableStateFlow<PlayerOrientation> =
        MutableStateFlow(PlayerOrientation.ALL)
    val selectedPlayerOrientation: StateFlow<PlayerOrientation> = _selectedPlayerOrientation.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            launch { getThemeSettings() }
            launch { getPlayerOrientation() }
        }
    }

    private fun getThemeSettings() {
        themeSettingsUseCase.theme.onEach {
            _selectedTheme.value = it
        }.launchIn(viewModelScope)
    }

    private fun getPlayerOrientation() {
        playerOrientationSettingsUseCase.playerOrientation.onEach {
            _selectedPlayerOrientation.value = it
        }.launchIn(viewModelScope)
    }

    fun updateThemeSettings(theme: ThemeType) {
        viewModelScope.launch {
            themeSettingsUseCase.updateTheme(theme)
        }
    }

    fun updatePlayerOrientation() {
        viewModelScope.launch {
            playerOrientationSettingsUseCase.updatePlayerOrientation(
                orientation = when(_selectedPlayerOrientation.value) {
                    PlayerOrientation.ALL -> PlayerOrientation.HORIZONTAL
                    PlayerOrientation.HORIZONTAL -> PlayerOrientation.ALL
                }
            )
        }
    }
}