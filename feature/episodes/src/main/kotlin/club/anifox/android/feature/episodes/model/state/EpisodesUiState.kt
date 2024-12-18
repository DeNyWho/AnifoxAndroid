package club.anifox.android.feature.episodes.model.state

internal data class EpisodesUiState(
    val url: String = "",
    val translationId: Int = 0,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)