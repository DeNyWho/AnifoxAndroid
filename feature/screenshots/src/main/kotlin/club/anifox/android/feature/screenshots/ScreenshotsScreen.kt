package club.anifox.android.feature.screenshots

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.screenshots.composable.grid.content.ScreenshotsGridContent
import club.anifox.android.feature.screenshots.param.ScreenshotsContentPreviewParam
import club.anifox.android.feature.screenshots.param.ScreenshotsContentProvider
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun ScreenshotsScreen(
    viewModel: ScreenshotsViewModel = hiltViewModel(),
    url: String = "",
    animeTitle: String? = null,
    onBackPressed: () -> Boolean,
) {
    LaunchedEffect(viewModel) {
        viewModel.getScreenshotAnime(url)
    }

    ScreenshotsUI(
        screenshotAnimeState = viewModel.screenshotsAnime.value,
        onBackPressed = onBackPressed,
        animeTitle = animeTitle,
    )
}

@Composable
private fun ScreenshotsUI(
    modifier: Modifier = Modifier,
    screenshotAnimeState: StateListWrapper<String>,
    onBackPressed: () -> Boolean,
    animeTitle: String?,
) {
    if(screenshotAnimeState.isLoading) {
        CircularProgress()
    } else {
        val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
        CollapsingToolbarScaffold(
            modifier = modifier
                .fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {
                SimpleTopBar(
                    onBackPressed = onBackPressed,
                    title = if(animeTitle == null) "" else "${stringResource(R.string.feature_screenshots_top_bar_title)} $animeTitle",
                )
            },
        ) {
            ScreenshotsContent(
                screenshotAnimeState = screenshotAnimeState,
            )
        }
    }
}

@Composable
internal fun ScreenshotsContent(
    modifier: Modifier = Modifier,
    screenshotAnimeState: StateListWrapper<String>,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp),
    ) {
        ScreenshotsGridContent(
            contentState = screenshotAnimeState,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewScreenshotsScreenUI(
    @PreviewParameter(ScreenshotsContentProvider::class) param: ScreenshotsContentPreviewParam
) {
    DefaultPreview(true) {
        ScreenshotsUI(
            screenshotAnimeState = param.screenshotsAnime,
            onBackPressed = param.onBackPressed,
            animeTitle = param.animeTitle,
        )
    }
}
