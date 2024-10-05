package club.anifox.android.feature.genres.data

internal data class SearchState(
    val genre: String = "",
    val minimalAge: Int? = null,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)