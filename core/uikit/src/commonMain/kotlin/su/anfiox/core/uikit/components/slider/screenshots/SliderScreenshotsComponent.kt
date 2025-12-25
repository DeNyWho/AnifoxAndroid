package su.anfiox.core.uikit.components.slider.screenshots

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import su.anfiox.core.uikit.components.card.screenshot.CardScreenshotLandscape
import su.anfiox.core.uikit.components.card.screenshot.CardScreenshotLandscapeDefaults
import su.anfiox.core.uikit.components.card.screenshot.showCardScreenshotLandscapeMoreWhenPastLimit
import su.anfiox.core.uikit.components.card.screenshot.showCardScreenshotLandscapeShimmer
import su.anfiox.core.uikit.components.slider.SliderComponentDefaults
import su.anfiox.core.uikit.components.slider.header.SliderHeader
import su.anfiox.core.uikit.components.slider.header.SliderHeaderShimmer
import su.anfiox.core.uikit.components.slider.screenshots.param.SliderScreenshotsComponentPreviewParam
import su.anfiox.core.uikit.components.slider.screenshots.param.SliderScreenshotsComponentProvider
import su.anfiox.core.uikit.util.DefaultPreview
import su.anfiox.core.uikit.util.onUpdateShimmerBounds
import su.anifox.domain.state.StateListWrapper

@Composable
fun SliderScreenshotsComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    headerTitle: String,
    screenshotsState: StateListWrapper<String>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentArrangement: Arrangement.Horizontal = CardScreenshotLandscapeDefaults.HorizontalArrangement.Default,
    onMoreClick: () -> Unit,
) {
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    // header
    if (screenshotsState.isLoading) {
        SliderHeaderShimmer(
            modifier = headerModifier,
            shimmerInstance = shimmer,
        )
    } else if (screenshotsState.data.isNotEmpty()) {
        SliderHeader(
            modifier = headerModifier,
            title = headerTitle,
        )
    }

    // content
    LazyRow(
        modifier = modifier.onUpdateShimmerBounds(shimmer),
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement,
    ) {
        if (screenshotsState.isLoading) {
            showCardScreenshotLandscapeShimmer(
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if (screenshotsState.data.isNotEmpty()) {
            items(
                screenshotsState.data,
                key = { it },
            ) { imageUrl ->
                CardScreenshotLandscape(
                    image = imageUrl,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = {
                        selectedImageIndex = screenshotsState.data.indexOf(imageUrl)
                    }
                )
            }
            showCardScreenshotLandscapeMoreWhenPastLimit(
                size = screenshotsState.data.size,
                onClick = {
                    onMoreClick.invoke()
                },
            )
        }
    }

    // TODO: REWORK TO KMP
//    if (selectedImageIndex != null) {
//        SwipeableImageDialog(
//            images = screenshotsState.data,
//            initialIndex = selectedImageIndex!!,
//            onDismiss = { selectedImageIndex = null },
//        )
//    }
}

@Preview
@Composable
private fun PreviewScrollableHorizontalContentScreenshots(
    @PreviewParameter(SliderScreenshotsComponentProvider::class)
    param: SliderScreenshotsComponentPreviewParam,
) {
    DefaultPreview(true) {
        SliderScreenshotsComponent(
            modifier = param.modifier,
            headerModifier = param.headerModifier,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            headerTitle = param.headerTitle,
            screenshotsState = param.contentState,
            contentPadding = param.contentPadding,
            contentArrangement = param.contentArrangement,
            onMoreClick = param.onMoreClick,
        )
    }
}
