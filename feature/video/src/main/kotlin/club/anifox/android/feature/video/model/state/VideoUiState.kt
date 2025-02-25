package club.anifox.android.feature.video.model.state

internal data class VideoUiState(
    val url: String = "",
    val animeTitle: String? = null,
    val isInitialized: Boolean = false,
)