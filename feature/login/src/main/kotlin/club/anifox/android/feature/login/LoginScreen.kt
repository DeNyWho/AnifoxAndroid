package club.anifox.android.feature.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.theme.AnifoxTheme

@Composable
internal fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    LoginUI()
}

@Composable
private fun LoginUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Login Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewLoginUI() {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            LoginUI()
        }
    }
}
