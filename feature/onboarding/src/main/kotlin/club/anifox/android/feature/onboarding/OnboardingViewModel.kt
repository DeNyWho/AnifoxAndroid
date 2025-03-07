package club.anifox.android.feature.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import club.anifox.android.domain.usecase.user.UserFirstLaunchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class OnboardingViewModel @Inject constructor(
    private val userFirstLaunchUseCase: UserFirstLaunchUseCase,
) : ViewModel() {
    fun updateFirstLaunch() {
        viewModelScope.launch {
            userFirstLaunchUseCase.setFirstLaunchCompleted()
        }
    }
}