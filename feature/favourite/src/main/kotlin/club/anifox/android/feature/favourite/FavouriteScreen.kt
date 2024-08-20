package club.anifox.android.feature.favourite

import android.content.pm.ActivityInfo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.button.AnifoxButtonPrimary
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.core.uikit.util.LockScreenOrientation
import club.anifox.android.feature.favourite.param.FavouriteContentPreviewParam
import club.anifox.android.feature.favourite.param.FavouriteContentProvider
import club.anifox.android.feature.favourite.state.ScreenState

@Composable
internal fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
) {
    val screenState by viewModel.screenState.collectAsState(initial = ScreenState.Loading)
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

    FavouriteUI(
        screenState = screenState,
    )
}

@Composable
private fun FavouriteUI(screenState: ScreenState) {
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
                NotAuthenticatedUI()
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

@Composable
private fun NotAuthenticatedUI() {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier
                .padding(end = 24.dp)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(club.anifox.android.core.uikit.R.drawable.anifox_logo),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.feature_favourite_not_authenticated_title),
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 40.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.feature_favourite_not_authenticated_sub_title),
            style = MaterialTheme.typography.titleSmall,
        )
        AnifoxButtonPrimary(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                .width(200.dp),
            onClick = {

            },
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(0.dp),
        ) {
            Text(
                text = stringResource(R.string.feature_favourite_not_authenticated_button_auth),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        AnifoxButtonPrimary(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .width(200.dp),
            onClick = {

            },
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(0.dp),
        ) {
            Text(
                text = stringResource(R.string.feature_favourite_not_authenticated_button_reg),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
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
            FavouriteUI(param.screenState)
        }
    }
}
