package club.anifox.android.feature.translations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.feature.translations.param.TranslationsContentPreviewParam
import club.anifox.android.feature.translations.param.TranslationsContentProvider

@Composable
internal fun TranslationsScreen(
    viewModel: TranslationsViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
) {
    TranslationsUI(
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun TranslationsUI(
    onBackPressed: () -> Boolean,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "Translations Screen",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewTranslationsUI(
    @PreviewParameter(TranslationsContentProvider::class) param: TranslationsContentPreviewParam,
) {
    DefaultPreview(true) {
        TranslationsUI(
            onBackPressed = param.onBackPressed,
        )
    }
}
