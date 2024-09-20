package club.anifox.android.domain.repository.user

import club.anifox.android.domain.model.common.device.FontSizePrefs
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val isFirstLaunch: Flow<Boolean>
    suspend fun updateFirstLaunch(isFirstLaunch: Boolean)
    val fontSizePrefs: Flow<FontSizePrefs>
    suspend fun updateFontSizePrefs(fontSizePrefs: FontSizePrefs)
}