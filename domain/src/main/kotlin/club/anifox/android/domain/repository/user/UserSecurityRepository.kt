package club.anifox.android.domain.repository.user

import kotlinx.coroutines.flow.Flow
import club.anifox.android.domain.state.Result

interface UserSecurityRepository {
    fun getAccessToken(): Flow<Result<String>>
    fun getRefreshToken(): Flow<Result<String>>
    suspend fun saveAccessToken(accessToken: String): Result<Unit>
    suspend fun saveRefreshToken(refreshToken: String): Result<Unit>
}