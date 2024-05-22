package club.anifox.android.domain.state

import club.anifox.android.domain.model.common.ApiError

data class StateListWrapper<T>(
    val data: List<T> = listOf(),
    val isLoading: Boolean = false,
    val error: ApiError = ApiError(),
) {
    companion object {
        inline fun <reified T> loading(): StateListWrapper<T> {
            return StateListWrapper(isLoading = true)
        }
    }
}
