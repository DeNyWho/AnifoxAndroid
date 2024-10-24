package club.anifox.android.feature.translations

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.translations.AnimeTranslationsCount
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.translations.composable.item.TranslationItem
import club.anifox.android.feature.translations.param.TranslationsContentPreviewParam
import club.anifox.android.feature.translations.param.TranslationsContentProvider

@Composable
internal fun TranslationsScreen(
    viewModel: TranslationsViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    onTranslationClick: (Int) -> Unit,
    url: String,
) {
    val animeTranslationsCount by viewModel.animeTranslationsCount.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.getTranslationsCount(url)
    }

    TranslationsUI(
        onBackPressed = onBackPressed,
        animeTranslationsCount = animeTranslationsCount,
        onTranslationClick = onTranslationClick,
    )
}

@Composable
private fun TranslationsUI(
    onBackPressed: () -> Boolean,
    animeTranslationsCount: StateListWrapper<AnimeTranslationsCount>,
    onTranslationClick: (Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SimpleTopBar(
                onBackPressed = onBackPressed,
                title = stringResource(R.string.feature_translations_top_bar_title),
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(start = 16.dp, end = 16.dp, top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            if(animeTranslationsCount.isLoading) {

            } else if(animeTranslationsCount.data.isNotEmpty()) {
                items(
                    animeTranslationsCount.data,
                    key = { it.translation.id },
                ) { translation ->
                    TranslationItem(
                        translation = translation,
                        onClick = onTranslationClick,
                    )
                }
            }
        }
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
            animeTranslationsCount = param.animeTranslationsCount,
            onTranslationClick = param.onTranslationClick,
        )
    }
}
