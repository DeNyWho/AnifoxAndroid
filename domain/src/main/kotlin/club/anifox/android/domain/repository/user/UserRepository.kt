package club.anifox.android.domain.repository.user

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    val isFirstLaunch: Flow<Boolean>
    suspend fun updateFirstLaunch(isFirstLaunch: Boolean)
}