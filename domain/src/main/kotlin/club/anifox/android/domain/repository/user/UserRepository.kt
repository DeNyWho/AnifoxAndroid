package club.anifox.android.domain.repository.user

import club.anifox.android.domain.model.common.device.FontSizePrefs
import club.anifox.android.domain.model.common.device.ThemeType
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val isFirstLaunch: Flow<Boolean>
    suspend fun updateFirstLaunch(isFirstLaunch: Boolean)
    val fontSizePrefs: Flow<FontSizePrefs>
    suspend fun updateFontSizePrefs(fontSizePrefs: FontSizePrefs)
    suspend fun updateTheme(theme: ThemeType)
    val theme: Flow<ThemeType>
}