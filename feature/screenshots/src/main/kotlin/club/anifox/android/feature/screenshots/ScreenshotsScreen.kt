package club.anifox.android.feature.screenshots

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.screenshots.component.item.CardScreenshotGridComponentItem
import club.anifox.android.feature.screenshots.component.item.showCardScreenshotGridIComponentItemShimmer
import club.anifox.android.feature.screenshots.param.ScreenshotsUIPreviewParam
import club.anifox.android.feature.screenshots.param.ScreenshotsUIProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun ScreenshotsScreen(
    viewModel: ScreenshotsViewModel = hiltViewModel(),
    url: String = "",
    animeTitle: String? = null,
    onBackPressed: () -> Unit,
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
    onBackPressed: () -> Unit,
    animeTitle: String?,
) {
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

@Composable
internal fun ScreenshotsContentUI(
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
    screenshotAnimeState: StateListWrapper<String>,
) {
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    val screenInfo = LocalScreenInfo.current
    val configuration = LocalConfiguration.current

    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val columns = when {
        screenInfo.screenType == ScreenType.EXTRA_LARGE && isPortrait -> 3
        screenInfo.screenType == ScreenType.EXTRA_LARGE && !isPortrait -> 5
        !isPortrait -> 4
        else -> 2
    }

    LazyVerticalGrid(
        modifier = GridComponentDefaults.Default.fillMaxSize(),
        columns = GridCells.Fixed(columns),
        horizontalArrangement = CardScreenshotLandscapeDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardScreenshotLandscapeDefaults.VerticalArrangement.Grid,
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier)
        }

        if(screenshotAnimeState.isLoading) {
            showCardScreenshotGridIComponentItemShimmer(shimmerInstance = shimmer)
        } else if(screenshotAnimeState.data.isNotEmpty()) {
            items(
                screenshotAnimeState.data,
                key = { it },
            ) { imageUrl ->
                CardScreenshotGridComponentItem(
                    image = imageUrl,
                    onClick = {
                        selectedImageIndex = screenshotAnimeState.data.indexOf(imageUrl)
                    },
                )
            }
        }
    }

    if (selectedImageIndex != null) {
        SwipeableImageDialog(
            images = screenshotAnimeState.data,
            initialIndex = selectedImageIndex!!,
            onDismiss = { selectedImageIndex = null },
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
