package club.anifox.android.feature.onboarding

import android.content.pm.ActivityInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.button.AnifoxButtonPrimary
import club.anifox.android.core.uikit.icon.AnifoxIcons
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LockScreenOrientation

@Composable
internal fun OnboardingScreen(
    viewModel: OnboardingViewModel = hiltViewModel(),
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    OnboardingUI(
        onSkipClick = { viewModel.updateFirstLaunch() },
        onLoginClick = onLoginClick,
        onRegistrationClick = onRegistrationClick,
    )
}

@Composable
private fun OnboardingUI(
    onSkipClick: () -> Unit,
    onLoginClick: () -> Unit,
    onRegistrationClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 80.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(AnifoxIcons.logo),
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 40.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                Text(
                    text = stringResource(R.string.feature_onboarding_title_main),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.feature_onboarding_title_sub_main),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AnifoxButtonPrimary(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .fillMaxWidth(),
                onClick = {
                    onRegistrationClick.invoke()
                },
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                ),
                paddingValues = PaddingValues(0.dp),
            ) {
                Text(
                    text = stringResource(R.string.feature_onboarding_button_registration_title),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.feature_onboarding_button_login_title),
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    modifier = Modifier
                        .clickable {
                            onLoginClick.invoke()
                        }
                        .padding(start = 8.dp),
                    text = stringResource(R.string.feature_onboarding_button_login_sub_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                modifier = Modifier
                    .clickable {
                        onSkipClick.invoke()
                    }
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp),
                text = stringResource(R.string.feature_onboarding_button_skip_title),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewOnboardingUI() {
    DefaultPreview {
        OnboardingUI(
            onSkipClick = { },
            onLoginClick = { },
            onRegistrationClick = { },
        )
    }
}
