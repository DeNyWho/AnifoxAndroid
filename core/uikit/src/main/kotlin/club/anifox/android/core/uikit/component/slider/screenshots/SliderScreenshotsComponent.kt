package club.anifox.android.core.uikit.component.slider.screenshots

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscape
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import club.anifox.android.core.uikit.component.card.screenshot.showCardScreenshotLandscapeMoreWhenPastLimit
import club.anifox.android.core.uikit.component.card.screenshot.showCardScreenshotLandscapeShimmer
import club.anifox.android.core.uikit.component.dialog.gallery.SwipeableImageDialog
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.component.slider.screenshots.param.SliderScreenshotsComponentPreviewParam
import club.anifox.android.core.uikit.component.slider.screenshots.param.SliderScreenshotsComponentProvider
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.onUpdateShimmerBounds
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

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

    if (selectedImageIndex != null) {
        SwipeableImageDialog(
            images = screenshotsState.data,
            initialIndex = selectedImageIndex!!,
            onDismiss = { selectedImageIndex = null },
        )
    }
}

@PreviewLightDark
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
