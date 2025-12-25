package su.anfiox.core.uikit.components.error

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.util.DefaultPreview
import su.anifox.core.uikit.generated.resources.Res
import su.anifox.core.uikit.generated.resources.core_uikit_error_no_search_result

@Composable
fun NoSearchResultsError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(Res.string.core_uikit_error_no_search_result),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
        )
    }
}

@Preview
@Composable
private fun PreviewNoSearchResultsError() {
    DefaultPreview {
        NoSearchResultsError()
    }
}