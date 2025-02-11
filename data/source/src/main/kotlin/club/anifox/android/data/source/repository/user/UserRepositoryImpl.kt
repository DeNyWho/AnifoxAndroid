package club.anifox.android.data.source.repository.user

import club.anifox.android.data.datastore.source.UserDataSource
import club.anifox.android.domain.model.common.device.FontSizePrefs
import club.anifox.android.domain.model.common.device.PlayerOrientation
import club.anifox.android.domain.model.common.device.ThemeType
import club.anifox.android.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
) : UserRepository {
    private val userData = userDataSource.userData

    override val isFirstLaunch: Flow<Boolean>
        get() = userData["is_first_launch"]?.mapNotNull { it as? Boolean }
            ?: flow { emit(true) }

    override val fontSizePrefs: Flow<FontSizePrefs>
        get() = userData["font_size_prefs"]?.mapNotNull { it as? FontSizePrefs }
            ?: flow { emit(FontSizePrefs.DEFAULT) }

    override val theme: Flow<ThemeType>
        get() = userData["theme"]?.mapNotNull { it as? ThemeType }
            ?: flow { emit(ThemeType.SYSTEM) }

    override val playerOrientation: Flow<PlayerOrientation>
        get() = userData["player_orientation"]?.mapNotNull { it as? PlayerOrientation }
            ?: flow { emit(PlayerOrientation.ALL) }

    override suspend fun updateFirstLaunch(isFirstLaunch: Boolean) =
        userDataSource.updateFirstLaunch(isFirstLaunch)

    override suspend fun updateFontSizePrefs(fontSizePrefs: FontSizePrefs) =
        userDataSource.updateFontSizePrefs(fontSizePrefs)

    override suspend fun updateTheme(theme: ThemeType) =
        userDataSource.updateTheme(theme)

    override suspend fun updatePlayerOrientation(orientation: PlayerOrientation) =
        userDataSource.updatePlayerOrientation(orientation)
}
