package club.anifox.android.data.source.repository.user

import club.anifox.android.data.datastore.source.UserSecurityDataSource
import club.anifox.android.domain.repository.UserSecurityRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserSecurityRepositoryImpl @Inject constructor(
    private val userSecurityDataSource: UserSecurityDataSource,
) : UserSecurityRepository {

    override fun getAccessToken(): Flow<Result<String>> {
        return userSecurityDataSource.accessToken
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }

    override fun getRefreshToken(): Flow<Result<String>> {
        return userSecurityDataSource.refreshToken
            .map { Result.success(it) }
            .catch { emit(Result.failure(it)) }
    }

    override suspend fun saveAccessToken(accessToken: String): Result<Unit> {
        return try {
            userSecurityDataSource.saveAccessToken(accessToken)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String): Result<Unit> {
        return try {
            userSecurityDataSource.saveRefreshToken(refreshToken)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}