package club.anifox.android.feature.screenshots

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons.AutoMirrored.Filled
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.component.progress.CircularProgress
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
        onScreenshotClick = { },
        onBackPressed = onBackPressed,
        animeTitle = animeTitle,
    )
}

@Composable
private fun ScreenshotsUI(
    modifier: Modifier = Modifier,
    screenshotAnimeState: StateListWrapper<String>,
    onScreenshotClick: (String) -> Unit,
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
                        .statusBarsPadding(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AnifoxIconPrimary(
                        imageVector = Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .clickable {
                                onBackPressed.invoke()
                            }
                            .size(24.dp),
                    )

                    Text(
                        text = if(animeTitle == null) "" else "${stringResource(R.string.feature_screenshots_top_bar_title)} $animeTitle",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .weight(1f)
                            .padding(start = 16.dp, end = 12.dp),
                    )
                }
            },
        ) {
            ScreenshotsContent(
                screenshotAnimeState = screenshotAnimeState,
                onScreenshotClick = onScreenshotClick,
            )
        }
    }
}

@Composable
internal fun ScreenshotsContent(
    modifier: Modifier = Modifier,
    screenshotAnimeState: StateListWrapper<String>,
    onScreenshotClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp),
    ) {
        ScreenshotsGridContent(
            contentState = screenshotAnimeState,
            onItemClick = onScreenshotClick,
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
            onScreenshotClick = param.onScreenshotClick,
            onBackPressed = param.onBackPressed,
            animeTitle = param.animeTitle,
        )
    }
}
