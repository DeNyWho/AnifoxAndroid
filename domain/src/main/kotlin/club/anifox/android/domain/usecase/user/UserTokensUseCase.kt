package club.anifox.android.domain.usecase.user

import club.anifox.android.domain.repository.UserSecurityRepository
import kotlinx.coroutines.flow.Flow

class UserTokensUseCase(private val userSecurityRepository: UserSecurityRepository) {

    val accessToken: Flow<Result<String>> = userSecurityRepository.getAccessToken()

    val refreshToken: Flow<Result<String>> = userSecurityRepository.getRefreshToken()

    suspend fun saveAccessToken(accessToken: String): Result<Unit> {
        return userSecurityRepository.saveAccessToken(accessToken)
    }

    suspend fun saveRefreshToken(refreshToken: String): Result<Unit> {
        return userSecurityRepository.saveRefreshToken(refreshToken)
    }
}