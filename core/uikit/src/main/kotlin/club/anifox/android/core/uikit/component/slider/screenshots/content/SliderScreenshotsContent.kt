package club.anifox.android.core.uikit.component.slider.screenshots.content

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
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.component.slider.screenshots.content.param.SliderScreenshotsContentPreviewParam
import club.anifox.android.core.uikit.component.slider.screenshots.content.param.SliderScreenshotsContentProvider
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.onUpdateShimmerBounds
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun SliderScreenshotsContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    headerTitle: String,
    contentState: StateListWrapper<String>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentArrangement: Arrangement.Horizontal = CardScreenshotLandscapeDefaults.HorizontalArrangement.Default,
    onMoreClick: () -> Unit,
) {
    var selectedImageIndex by remember { mutableStateOf<Int?>(null) }

    // header
    if(contentState.isLoading) {
        SliderHeaderShimmer(
            modifier = headerModifier,
            shimmerInstance = shimmer,
        )
    } else if (contentState.data.isNotEmpty()) {
        SliderHeader(
            modifier = headerModifier,
            title = headerTitle,
        )
    }

    // content
    LazyRow (
        modifier = modifier.onUpdateShimmerBounds(shimmer),
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement,
    ) {
        if(contentState.isLoading) {
            showCardScreenshotLandscapeShimmer(
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if(contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it },
            ) { imageUrl ->
                CardScreenshotLandscape(
                    image = imageUrl,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = {
                        selectedImageIndex = contentState.data.indexOf(imageUrl)
                    }
                )
            }
            showCardScreenshotLandscapeMoreWhenPastLimit(
                size = contentState.data.size,
                onClick = {
                    onMoreClick.invoke()
                },
            )
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

@PreviewLightDark
@Composable
private fun PreviewScrollableHorizontalContentScreenshots(
    @PreviewParameter(SliderScreenshotsContentProvider::class) param: SliderScreenshotsContentPreviewParam,
) {
    DefaultPreview(true) {
        SliderScreenshotsContent (
            modifier = param.modifier,
            headerModifier = param.headerModifier,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            headerTitle = param.headerTitle,
            contentState = param.contentState,
            contentPadding = param.contentPadding,
            contentArrangement = param.contentArrangement,
            onMoreClick = param.onMoreClick,
        )
    }
}
