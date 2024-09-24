package club.anifox.android.feature.catalog

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
internal fun CatalogScreen(
    viewModel: CatalogViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
) {
    CatalogUI()
}

@Composable
private fun CatalogUI() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Catalog Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewCatalogUI() {
    DefaultPreview(true) {
        CatalogUI()
    }
}
