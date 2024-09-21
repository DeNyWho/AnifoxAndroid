package club.anifox.android.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.button.AnifoxButtonPrimary
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val userIdentifier = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }

    LoginUI(
        userIdentifier = userIdentifier,
        password = password,
    )
}

@Composable
private fun LoginUI(userIdentifier: MutableState<String>, password: MutableState<String>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier)
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier
                    .padding(end = 24.dp)
                    .align(Alignment.CenterHorizontally),
                painter = painterResource(club.anifox.android.core.uikit.R.drawable.core_uikit_anifox_logo),
                contentDescription = null,
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .widthIn(0.dp, 600.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
                value = userIdentifier.value,
                leadingIcon = { AnifoxIconPrimary(imageVector = Filled.Email, contentDescription = "emailIcon") },
                onValueChange = {
                    userIdentifier.value = it
                },
                label = { Text(text = stringResource(R.string.feature_login_textfield_login_hint)) },
                placeholder = { Text(text = stringResource(R.string.feature_login_textfield_login_hint)) },
            )
            OutlinedTextField(
                modifier = Modifier
                    .padding(start = 32.dp, end = 32.dp, top = 8.dp)
                    .widthIn(0.dp, 600.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                singleLine = true,
                value = password.value,
                leadingIcon = { AnifoxIconPrimary(imageVector = Filled.Password, contentDescription = "emailIcon") },
                onValueChange = {
                    password.value = it
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                label = { Text(text = stringResource(R.string.feature_login_textfield_password_hint)) },
                placeholder = { Text(text = stringResource(R.string.feature_login_textfield_password_hint)) },
            )
            AnifoxButtonPrimary(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, top = 24.dp)
                    .width(300.dp),
                onClick = {

                },
                shape = MaterialTheme.shapes.medium,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                ),
                paddingValues = PaddingValues(0.dp),
            ) {
                Text(
                    text = stringResource(R.string.feature_login_button_auth),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        Column(
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.feature_login_button_login_title),
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    modifier = Modifier
                        .clickable {

                        }
                        .padding(start = 8.dp),
                    text = stringResource(R.string.feature_login_button_login_sub_title),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewLoginUI() {
    DefaultPreview(true) {
        LoginUI(
            rememberSaveable { mutableStateOf("") },
            rememberSaveable { mutableStateOf("") },
        )
    }
}
