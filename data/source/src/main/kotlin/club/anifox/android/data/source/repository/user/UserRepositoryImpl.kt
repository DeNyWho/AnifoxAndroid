package club.anifox.android.data.source.repository.user

import club.anifox.android.data.datastore.source.UserDataSource
import club.anifox.android.domain.repository.user.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDataSource: UserDataSource,
): UserRepository {
    private val userData = userDataSource.userData

    override val isFirstLaunch: Flow<Boolean>
        get() = userData["is_first_launch"]?.mapNotNull { it as? Boolean } ?: throw IllegalStateException("is_first_launch Flow<Boolean> is null or does not contain Boolean")

    override suspend fun updateFirstLaunch(isFirstLaunch: Boolean) =
        userDataSource.updateFirstLaunch(isFirstLaunch)

}
