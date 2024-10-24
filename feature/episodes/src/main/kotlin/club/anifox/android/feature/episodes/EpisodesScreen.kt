package club.anifox.android.feature.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
internal fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    url: String,
    translationId: Int,
//    onEpisodeClick: (String) -> Unit,
) {
    EpisodesUI()
}

@Composable
private fun EpisodesUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Episodes Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewEpisodesUI() {
    DefaultPreview(true) {
        EpisodesUI()
    }
}
