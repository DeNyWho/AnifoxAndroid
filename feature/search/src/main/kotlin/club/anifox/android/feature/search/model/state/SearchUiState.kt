package club.anifox.android.feature.search.model.state

internal data class SearchUiState(
    val query: String = "",
    val showDefaultState: Boolean = true,
    val showLoading: Boolean = false,
    val showNoMoviesFound: Boolean = false,
    val errorMessage: String? = null,
)