package club.anifox.android.data.source.repository.user

import club.anifox.android.data.datastore.source.UserSecurityDataSource
import club.anifox.android.domain.repository.user.UserSecurityRepository
import club.anifox.android.domain.state.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class UserSecurityRepositoryImpl @Inject constructor(
    private val userSecurityDataSource: UserSecurityDataSource,
) : UserSecurityRepository {

    override fun getAccessToken(): Flow<Result<String>> {
        return try {
            userSecurityDataSource.accessToken
                .map { token ->
                    Result.Success(token)
                }
        } catch (exception: Throwable) {
            flow {
                emit(Result.Failure(exception) as Result<String>)
            }
        }
    }

    override fun getRefreshToken(): Flow<Result<String>> {
        return try {
            userSecurityDataSource.accessToken
                .map { token ->
                    Result.Success(token)
                }
        } catch (exception: Throwable) {
            flow {
                emit(Result.Failure(exception) as Result<String>)
            }
        }
    }

    override suspend fun saveAccessToken(accessToken: String): Result<Unit> {
        return try {
            userSecurityDataSource.saveAccessToken(accessToken)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun saveRefreshToken(refreshToken: String): Result<Unit> {
        return try {
            userSecurityDataSource.saveRefreshToken(refreshToken)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }
}