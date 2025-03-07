package club.anifox.android.domain.usecase.settings

import club.anifox.android.domain.model.common.device.ThemeType
import club.anifox.android.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class ThemeSettingsUseCase(private val userRepository: UserRepository) {
    val theme: Flow<ThemeType> = userRepository.theme

    suspend fun updateTheme(theme: ThemeType) {
        userRepository.updateTheme(theme)
    }
}