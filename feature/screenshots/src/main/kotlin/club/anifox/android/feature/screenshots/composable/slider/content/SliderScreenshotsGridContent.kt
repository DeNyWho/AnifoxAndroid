package club.anifox.android.feature.screenshots.composable.slider.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.slider.screenshots.content.param.SliderScreenshotsContentPreviewParam
import club.anifox.android.core.uikit.component.slider.screenshots.content.param.SliderScreenshotsContentProvider
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.screenshots.composable.slider.item.CardScreenshotGridItem
import club.anifox.android.feature.screenshots.composable.slider.item.showCardScreenshotGridItemShimmer
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun SliderScreenshotsGridContent(
    modifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    contentState: StateListWrapper<String>,
    onItemClick: (String) -> Unit
) {
    val columnCount = calculateSliderScreenshotsGridCount(columnWidth = 150.dp)
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(columnCount),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    onClick = { onItemClick.invoke(imageUrl) }
                )
            }
        }
    }
}

@Composable
private fun calculateSliderScreenshotsGridCount(columnWidth: Dp): Int {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    return (screenWidth / columnWidth).toInt().coerceIn(2, 3)
}

@PreviewLightDark
@Composable
private fun PreviewScrollableHorizontalContentScreenshots(
    @PreviewParameter(SliderScreenshotsContentProvider::class) param: SliderScreenshotsContentPreviewParam,
) {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SliderScreenshotsGridContent (
                modifier = param.modifier,
                contentState = param.contentState,
                onItemClick = param.onItemClick,
            )
        }
    }
}
