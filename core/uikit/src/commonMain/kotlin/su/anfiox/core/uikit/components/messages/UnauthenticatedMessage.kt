package su.anfiox.core.uikit.components.messages

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.components.button.AnifoxButtonPrimary
import su.anfiox.core.uikit.util.DefaultPreview
import su.anifox.core.uikit.generated.resources.Res
import su.anifox.core.uikit.generated.resources.core_uikit_not_authenticated_button_auth
import su.anifox.core.uikit.generated.resources.core_uikit_not_authenticated_button_reg
import su.anifox.core.uikit.generated.resources.core_uikit_not_authenticated_sub_title
import su.anifox.core.uikit.generated.resources.core_uikit_not_authenticated_title

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
        // TODO: ADD ICONS
//        Image(
//            modifier = Modifier
//                .padding(end = 24.dp)
//                .align(Alignment.CenterHorizontally),
//            painter = painterResource(AnifoxIcons.logo),
//            contentDescription = null,
//        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            text = stringResource(Res.string.core_uikit_not_authenticated_title),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 40.dp, vertical = 8.dp),
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.core_uikit_not_authenticated_sub_title),
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
                text = stringResource(Res.string.core_uikit_not_authenticated_button_auth),
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
                text = stringResource(Res.string.core_uikit_not_authenticated_button_reg),
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewUnauthenticatedMessage() {
    DefaultPreview {
        UnauthenticatedMessage(
            onLoginClick = { },
            onRegistrationClick = { },
        )
    }
}