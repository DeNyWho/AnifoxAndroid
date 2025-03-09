package club.anifox.android.feature.search.model.state

internal data class SearchUiState(
    val query: String = "",
    val isSearching: Boolean = false,
    val isSearchBarFocused: Boolean = false,
)