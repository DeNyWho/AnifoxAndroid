package club.anifox.android.feature.search.model.state

internal data class SearchUiState(
    val query: String = "",
    val previousQuery: String = "",
    val isLoading: Boolean = false,
)