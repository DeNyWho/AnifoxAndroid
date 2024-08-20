package club.anifox.android.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.state.Result
import club.anifox.android.domain.usecase.user.UserTokensUseCase
import club.anifox.android.feature.profile.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ProfileViewModel @Inject constructor(
    private val userTokensUseCase: UserTokensUseCase
) : ViewModel() {
    private val _screenState = MutableSharedFlow<ScreenState>(replay = 1)
    val screenState: SharedFlow<ScreenState> = _screenState.asSharedFlow()

    init {
        viewModelScope.launch {
            userTokensUseCase.refreshToken
                .map { result ->
                    when (result) {
                        is Result.Success -> {
                            if (result.data.isNotBlank()) {
                                ScreenState.Authenticated
                            } else {
                                ScreenState.NotAuthenticated
                            }
                        }
                        is Result.Failure -> ScreenState.Error(result.exception)
                        Result.Loading -> ScreenState.Loading
                    }
                }
                .catch { exception ->
                    _screenState.emit(ScreenState.Error(exception))
                }
                .collect { screenState ->
                    _screenState.emit(screenState)
                }
        }
    }
}