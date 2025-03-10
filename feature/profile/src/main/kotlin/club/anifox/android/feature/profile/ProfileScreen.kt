package club.anifox.android.feature.profile

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.messages.UnauthenticatedMessage
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LockScreenOrientation
import club.anifox.android.feature.profile.param.ProfileContentPreviewParam
import club.anifox.android.feature.profile.param.ProfileContentProvider
import club.anifox.android.feature.profile.state.ScreenState

@Composable
internal fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    val screenState by viewModel.screenState.collectAsState(initial = ScreenState.Loading)
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    ProfileUI(
        screenState = screenState,
        onLoginClick = onLoginClick,
        onRegistrationClick = onRegistrationClick,
    )
}

@Composable
private fun ProfileUI(
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
private fun PreviewProfileUI(
    @PreviewParameter(ProfileContentProvider::class) param: ProfileContentPreviewParam,
) {
    DefaultPreview(true) {
        ProfileUI(
            screenState = param.screenState,
            onLoginClick = { },
            onRegistrationClick = { },
        )
    }
}
