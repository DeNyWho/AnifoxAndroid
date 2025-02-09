package club.anifox.android.feature.screenshots

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.screenshots.component.grid.ScreenshotsGridComponent
import club.anifox.android.feature.screenshots.param.ScreenshotsUIPreviewParam
import club.anifox.android.feature.screenshots.param.ScreenshotsUIProvider
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
                SimpleTopBarCollapse(
                    title = if(animeTitle == null) "" else "${stringResource(R.string.feature_screenshots_top_bar_title)} $animeTitle",
                    toolbarScaffoldState = toolbarScaffoldState,
                    onBackPressed = onBackPressed,
                )
            },
            toolbarModifier = Modifier.toolbarShadow(
                shadowElevation = 4.dp,
                tonalElevation = 4.dp,
                shape = RectangleShape,
            ),
            body = {
                ScreenshotsContentUI(
                    screenshotAnimeState = screenshotAnimeState,
                )
            },
        )
    }
}

@Composable
internal fun ScreenshotsContentUI(
    modifier: Modifier = Modifier,
    screenshotAnimeState: StateListWrapper<String>,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp),
    ) {
        ScreenshotsGridComponent(
            contentState = screenshotAnimeState,
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewScreenshotsUI(
    @PreviewParameter(ScreenshotsUIProvider::class) param: ScreenshotsUIPreviewParam
) {
    DefaultPreview(true) {
        ScreenshotsUI(
            screenshotAnimeState = param.screenshotsAnime,
            onBackPressed = param.onBackPressed,
            animeTitle = param.animeTitle,
        )
    }
}
