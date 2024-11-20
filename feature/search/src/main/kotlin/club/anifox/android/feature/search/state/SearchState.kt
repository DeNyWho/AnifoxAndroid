package club.anifox.android.feature.search.state

internal data class SearchState(
    val query: String = "",
    val isLoading: Boolean = false,
    val isInitial: Boolean = true,
)