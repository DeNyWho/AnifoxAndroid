package club.anifox.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.usecase.user.UserFirstLaunchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userFirstLaunchUseCase: UserFirstLaunchUseCase,
): ViewModel() {
    private val _isFirstLaunch = MutableStateFlow<Boolean?>(null)
    val isFirstLaunch: StateFlow<Boolean?> = _isFirstLaunch

    init {
        observeFirstTypeUI()
    }

    private fun observeFirstTypeUI() {
        viewModelScope.launch {
            userFirstLaunchUseCase.isFirstLaunch.collect { value ->
                _isFirstLaunch.value = value
            }
        }
    }
}
