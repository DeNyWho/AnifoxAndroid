package club.anifox.android.domain.state

import club.anifox.android.domain.model.common.request.ApiError
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

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
