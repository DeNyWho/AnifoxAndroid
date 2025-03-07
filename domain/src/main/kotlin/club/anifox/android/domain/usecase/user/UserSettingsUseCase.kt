package club.anifox.android.domain.usecase.user

import club.anifox.android.domain.model.common.device.FontSizePrefs
import club.anifox.android.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow

class UserSettingsUseCase(private val userRepository: UserRepository) {
    val fontSizePrefs: Flow<FontSizePrefs> = userRepository.fontSizePrefs

    suspend fun setFontSizePrefs(fontSizePrefs: FontSizePrefs) {
        userRepository.updateFontSizePrefs(fontSizePrefs)
    }
}
