package club.anifox.android.feature.characters.model.state

internal data class CharactersUiState(
    val selectedRole: String? = null,
    val url: String = "",
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)