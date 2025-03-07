package club.anifox.android.domain.usecase.settings

import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class PlayerOrientationSettingsUseCase(private val userRepository: UserRepository) {
    val playerOrientation: Flow<PlayerOrientation> = userRepository.playerOrientation

    suspend fun updatePlayerOrientation(orientation: PlayerOrientation) {
        userRepository.updatePlayerOrientation(orientation)
    }
}