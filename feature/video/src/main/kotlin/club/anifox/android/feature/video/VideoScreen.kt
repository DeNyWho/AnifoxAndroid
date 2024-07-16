package club.anifox.android.feature.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.theme.AnifoxTheme

@Composable
internal fun VideoScreen(
    viewModel: VideoViewModel = hiltViewModel(),
) {

}

@Composable
private fun VideoUI() {

}

@PreviewScreenSizes
@Composable
private fun PreviewVideoScreenUI() {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            VideoUI()
        }
    }
}