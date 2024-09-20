package club.anifox.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.model.common.device.FontSizePrefs
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.usecase.user.UserFirstLaunchUseCase
import club.anifox.android.domain.usecase.user.UserSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userFirstLaunchUseCase: UserFirstLaunchUseCase,
    private val userSettingsUseCase: UserSettingsUseCase
) : ViewModel() {
    private val _isFirstLaunch = MutableStateFlow<Boolean?>(null)
    val isFirstLaunch: StateFlow<Boolean?> = _isFirstLaunch

    val fontSizePrefs: StateFlow<FontSizePrefs> = userSettingsUseCase.fontSizePrefs.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FontSizePrefs.DEFAULT
    )

    init {
        viewModelScope.launch {
            userFirstLaunchUseCase.isFirstLaunch.collect { value ->
                _isFirstLaunch.value = value
            }
        }
    }

    fun onFirstLaunch(screenType: ScreenType) {
        viewModelScope.launch {
//            userFirstLaunchUseCase.setFirstLaunchCompleted()
            val initialFontSize = when (screenType) {
                ScreenType.SMALL -> FontSizePrefs.SMALL
                ScreenType.DEFAULT -> FontSizePrefs.DEFAULT
                ScreenType.LARGE -> FontSizePrefs.LARGE
                ScreenType.EXTRA_LARGE -> FontSizePrefs.EXTRA_LARGE
            }
            userSettingsUseCase.setFontSizePrefs(initialFontSize)
        }
    }
}
