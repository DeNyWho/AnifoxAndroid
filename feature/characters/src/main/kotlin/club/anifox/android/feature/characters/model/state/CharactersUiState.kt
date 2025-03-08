package club.anifox.android.feature.characters.model.state

internal data class CharactersUiState(
    val url: String = "",
    val searchQuery: String = "",
    val isInitialized: Boolean = false,
    val isLoading: Boolean = false,
)