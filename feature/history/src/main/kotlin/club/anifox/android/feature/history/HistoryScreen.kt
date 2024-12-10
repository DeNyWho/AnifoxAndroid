package club.anifox.android.feature.history

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
internal fun HistoryScreen(
    viewModel: HistoryViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    HistoryUI(
        onBackPressed = onBackPressed,
        onAnimeClick = onAnimeClick,
    )
}

@Composable
private fun HistoryUI(
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "History Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewHistoryUI() {
    DefaultPreview(true) {
        HistoryUI(
            onBackPressed = { false },
            onAnimeClick = { },
        )
    }
}
