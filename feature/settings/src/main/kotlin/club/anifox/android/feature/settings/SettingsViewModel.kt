package club.anifox.android.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.common.device.ThemeType
import club.anifox.android.domain.usecase.settings.ThemeSettingsUseCase
import club.anifox.android.domain.usecase.user.UserSettingsUseCase
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
    private val userSettingsUseCase: UserSettingsUseCase,
    private val themeSettingsUseCase: ThemeSettingsUseCase,
): ViewModel() {
    private val _selectedTheme: MutableStateFlow<ThemeType> =
        MutableStateFlow(ThemeType.SYSTEM)
    val selectedTheme: StateFlow<ThemeType> = _selectedTheme.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            launch { getThemeSettings() }
        }
    }

    private fun getThemeSettings() {
        themeSettingsUseCase.theme.onEach {
            _selectedTheme.value = it
        }.launchIn(viewModelScope)
    }

    fun updateThemeSettings(theme: ThemeType) {
        viewModelScope.launch {
            themeSettingsUseCase.updateTheme(theme)
        }
    }
}