package su.anifox.domain.state

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import su.anifox.domain.model.common.request.ApiError

data class StateListWrapper<T>(
    val data: ImmutableList<T> = persistentListOf(),
    val isLoading: Boolean = false,
    val error: ApiError? = null,
) {
    companion object {
        inline fun <reified T> loading(): StateListWrapper<T> {
            return StateListWrapper(isLoading = true)
        }
    }
}
