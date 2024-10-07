package club.anifox.android.feature.screenshots.composable.grid.content

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.screenshots.composable.grid.content.param.ScreenshotsGridContentPreviewParam
import club.anifox.android.feature.screenshots.composable.grid.content.param.ScreenshotsGridContentProvider
import club.anifox.android.feature.screenshots.composable.grid.item.CardScreenshotGridItem
import club.anifox.android.feature.screenshots.composable.grid.item.showCardScreenshotGridItemShimmer
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun ScreenshotsGridContent(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    contentState: StateListWrapper<String>,
) {
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    val minColumnSize = (screenWidth / 4).coerceAtLeast(CardScreenshotLandscapeDefaults.Width.Grid)

    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = minColumnSize),
        horizontalArrangement = CardScreenshotLandscapeDefaults.HorizontalArrangement.Grid,
        verticalArrangement = CardScreenshotLandscapeDefaults.VerticalArrangement.Grid,
    ) {
        if(contentState.isLoading) {
            showCardScreenshotGridItemShimmer(shimmerInstance = shimmer)
        } else if(contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it },
            ) { imageUrl ->
                CardScreenshotGridItem(
                    image = imageUrl,
                    onClick = {
                        selectedImageIndex = contentState.data.indexOf(imageUrl)
                    },
                )
            }
        }
    }

    if (selectedImageIndex != null) {
        SwipeableImageDialog(
            images = contentState.data,
            initialIndex = selectedImageIndex!!,
            onDismiss = { selectedImageIndex = null },
        )
    }
}

@PreviewScreenSizes
@Composable
private fun PreviewScrollableHorizontalContentScreenshots(
    @PreviewParameter(ScreenshotsGridContentProvider::class) param: ScreenshotsGridContentPreviewParam,
) {
    DefaultPreview(true) {
        ScreenshotsGridContent (
            contentState = param.contentState,
        )
    }
}
