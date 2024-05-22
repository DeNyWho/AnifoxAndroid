package club.anifox.android.domain.state

import club.anifox.android.domain.model.common.ApiError

data class StateWrapper<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ApiError = ApiError(),
) {
    companion object {
        inline fun <reified T> loading(): StateWrapper<T> {
            return StateWrapper(isLoading = true)
        }
    }
}
