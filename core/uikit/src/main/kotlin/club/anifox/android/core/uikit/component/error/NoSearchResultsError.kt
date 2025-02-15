package club.anifox.android.core.uikit.component.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import club.anifox.android.core.uikit.R
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
fun NoSearchResultsError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.core_uikit_error_no_search_result),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewNoSearchResultsError() {
    DefaultPreview {
        NoSearchResultsError()
    }
}