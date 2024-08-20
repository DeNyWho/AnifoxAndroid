package club.anifox.android.domain.usecase.user

import club.anifox.android.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class UserFirstLaunchUseCase(private val userRepository: UserRepository) {
    val isFirstLaunch: Flow<Boolean> = userRepository.isFirstLaunch

    suspend fun updateFirstLaunch(isFirstLaunch: Boolean) {
        userRepository.updateFirstLaunch(isFirstLaunch)
    }
}
