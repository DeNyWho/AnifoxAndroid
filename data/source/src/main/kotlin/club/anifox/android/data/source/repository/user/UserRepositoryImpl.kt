package club.anifox.android.data.source.repository.user

import club.anifox.android.data.datastore.source.UserDataSource
import club.anifox.android.domain.model.common.device.FontSizePrefs
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

    override suspend fun updateFirstLaunch(isFirstLaunch: Boolean) =
        userDataSource.updateFirstLaunch(isFirstLaunch)

    override suspend fun updateFontSizePrefs(fontSizePrefs: FontSizePrefs) =
        userDataSource.updateFontSizePrefs(fontSizePrefs)
}
