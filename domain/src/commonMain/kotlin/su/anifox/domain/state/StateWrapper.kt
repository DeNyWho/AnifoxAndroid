package su.anifox.domain.state

import su.anifox.domain.model.common.request.ApiError


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
