package club.anifox.android.feature.episodes.data

internal data class EpisodesState(
    val url: String = "",
    val translationId: Int = 0,
    val isLoading: Boolean = false,
    val isInitialized: Boolean = false,
)