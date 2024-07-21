package club.anifox.android.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.theme.AnifoxTheme

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
) {
    val searchQuery = rememberSaveable { mutableStateOf("") }

    SearchUI(
    )

    BackHandler {
        onBackPressed.invoke()
    }
}


@Composable
internal fun SearchUI(
    modifier: Modifier = Modifier,
) {

}

@Composable
internal fun SearchContent(
    modifier: Modifier,
) {

}

@PreviewScreenSizes
@Composable
private fun PreviewSearchScreenUI() {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SearchUI()
        }
    }
}
