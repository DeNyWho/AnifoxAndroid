package club.anifox.android.domain.state

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}