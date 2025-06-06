package club.anifox.android.core.uikit.component.messages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.R
import club.anifox.android.core.uikit.component.button.AnifoxButtonPrimary
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
fun UnauthenticatedMessage(
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    Column(
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
            painter = painterResource(AnifoxIcons.logo),
            contentDescription = null,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(R.string.core_uikit_not_authenticated_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 40.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.core_uikit_not_authenticated_sub_title),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground,
        )
        AnifoxButtonPrimary(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                .width(200.dp),
            onClick = {
                onLoginClick.invoke()
            },
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(0.dp),
        ) {
            Text(
                text = stringResource(R.string.core_uikit_not_authenticated_button_auth),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        AnifoxButtonPrimary(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .width(200.dp),
            onClick = {
                onRegistrationClick.invoke()
            },
            shape = MaterialTheme.shapes.medium,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp
            ),
            paddingValues = PaddingValues(0.dp),
        ) {
            Text(
                text = stringResource(R.string.core_uikit_not_authenticated_button_reg),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewUnauthenticatedMessage() {
    DefaultPreview {
        UnauthenticatedMessage(
            onLoginClick = { },
            onRegistrationClick = { },
        )
    }
}