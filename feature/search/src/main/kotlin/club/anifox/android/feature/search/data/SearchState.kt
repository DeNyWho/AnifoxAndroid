package club.anifox.android.feature.search.data

internal data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isInitial: Boolean = true,
)