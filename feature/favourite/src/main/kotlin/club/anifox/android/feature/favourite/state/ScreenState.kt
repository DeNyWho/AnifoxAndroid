package club.anifox.android.feature.favourite.state

internal sealed class ScreenState {
    data object Loading : ScreenState()
    data object Authenticated : ScreenState()
    data object NotAuthenticated : ScreenState()
    data class Error(val exception: Throwable) : ScreenState()
}