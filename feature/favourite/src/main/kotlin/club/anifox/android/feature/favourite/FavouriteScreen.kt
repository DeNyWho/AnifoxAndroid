package club.anifox.android.feature.favourite

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.messages.UnauthenticatedMessage
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.core.uikit.util.LockScreenOrientation
import club.anifox.android.feature.favourite.param.FavouriteContentPreviewParam
import club.anifox.android.feature.favourite.param.FavouriteContentProvider
import club.anifox.android.feature.favourite.state.ScreenState

@Composable
internal fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    val screenState by viewModel.screenState.collectAsState(initial = ScreenState.Loading)
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    FavouriteUI(
        screenState = screenState,
        onLoginClick = onLoginClick,
        onRegistrationClick = onRegistrationClick,
    )
}

@Composable
private fun FavouriteUI(
    screenState: ScreenState,
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (screenState) {
            is ScreenState.Loading -> {
                CircularProgressIndicator()
            }
            is ScreenState.Authenticated -> {
                AuthenticatedUI()
            }
            is ScreenState.NotAuthenticated -> {
                UnauthenticatedMessage(
                    onLoginClick = onLoginClick,
                    onRegistrationClick = onRegistrationClick,
                )
            }
            is ScreenState.Error -> {
                Log.e(
                    "Favourite Screen",
                    "${screenState.exception.message}"
                )
            }
        }
    }
}

@Composable
private fun AuthenticatedUI() {

}

@PreviewScreenSizes
@Composable
private fun PreviewFavouriteUI(
    @PreviewParameter(FavouriteContentProvider::class) param: FavouriteContentPreviewParam,
) {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            FavouriteUI(
                screenState = param.screenState,
                onLoginClick = { },
                onRegistrationClick = { },
            )
        }
    }
}
