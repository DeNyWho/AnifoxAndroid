package club.anifox.android.feature.player

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.feature.player.param.PlayerUiPreviewParam
import club.anifox.android.feature.player.param.PlayerUiProvider

@Composable
internal fun PlayerScreen(
    viewModel: PlayerViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    url: String,
) {
    LaunchedEffect(url) {
        Log.d("Player", "URL = $url")
    }

    PlayerUI(
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun PlayerUI(
    onBackPressed: () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Player Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewPlayerUI(
    @PreviewParameter(PlayerUiProvider::class) param: PlayerUiPreviewParam,
) {
    DefaultPreview(true) {
        PlayerUI(
            onBackPressed = param.onBackPressed,
        )
    }
}
