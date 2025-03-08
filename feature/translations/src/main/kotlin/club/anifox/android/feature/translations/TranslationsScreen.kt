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
import club.anifox.android.feature.translations.component.item.TranslationComponentItem
import club.anifox.android.feature.translations.component.item.showTranslationComponentItemShimmer
import club.anifox.android.feature.translations.param.TranslationsUIPreviewParam
import club.anifox.android.feature.translations.param.TranslationsUIProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun TranslationsScreen(
    viewModel: TranslationsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onTranslationClick: (String, Int) -> Unit,
    onPlayerClick: (String, Boolean?) -> Unit,
    url: String,
) {
    val uiState by viewModel.uiState.collectAsState()
    val animeTranslationsCountState by viewModel.animeTranslationsCount.collectAsState()

    LaunchedEffect(Unit) {
        if (!uiState.isInitialized) {
            viewModel.initialize(url)
        }
    }

    TranslationsUI(
        onBackPressed = onBackPressed,
        animeTranslationsCount = animeTranslationsCountState,
        onTranslationClick = { translationId ->
            onTranslationClick(url, translationId)
        },
        onPlayerClick = { link ->
            onPlayerClick.invoke(link, true)
        },
    )
}

@Composable
private fun TranslationsUI(
    onBackPressed: () -> Unit,
    animeTranslationsCount: StateListWrapper<AnimeTranslationsCount>,
    onTranslationClick: (Int) -> Unit,
    onPlayerClick: (String) -> Unit,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
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
            if (animeTranslationsCount.isLoading) {
                showTranslationComponentItemShimmer(
                    shimmerInstance = shimmer,
                )
            } else if (animeTranslationsCount.data.isNotEmpty()) {
                items(
                    animeTranslationsCount.data,
                    key = { it.translation.id },
                ) { translation ->
                    TranslationComponentItem(
                        translation = translation,
                        onClick = {
                            if (translation.link != null) onPlayerClick.invoke(translation.link!!)
                            else onTranslationClick.invoke(translation.translation.id)
                        },
                    )
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewTranslationsUI(
    @PreviewParameter(TranslationsUIProvider::class) param: TranslationsUIPreviewParam,
) {
    DefaultPreview(true) {
        TranslationsUI(
            onBackPressed = param.onBackPressed,
            animeTranslationsCount = param.animeTranslationsCount,
            onTranslationClick = param.onTranslationClick,
            onPlayerClick = param.onPlayerClick,
        )
    }
}
