package club.anifox.android.feature.search.model.state

internal data class SearchUiState(
    val query: String = "",
    val hasSearched: Boolean = false,
    val isSearchBarFocused: Boolean = false,
)