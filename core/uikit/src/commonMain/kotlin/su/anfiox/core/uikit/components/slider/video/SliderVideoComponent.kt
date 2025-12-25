package su.anfiox.core.uikit.components.slider.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import su.anfiox.core.uikit.components.card.video.CardVideoLandscape
import su.anfiox.core.uikit.components.card.video.CardVideoLandscapeDefaults
import su.anfiox.core.uikit.components.card.video.showCardVideoLandscapeMoreWhenPastLimit
import su.anfiox.core.uikit.components.card.video.showCardVideoLandscapeShimmer
import su.anfiox.core.uikit.components.slider.SliderComponentDefaults
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import su.anfiox.core.uikit.components.slider.header.SliderHeader
import su.anfiox.core.uikit.components.slider.header.SliderHeaderShimmer
import su.anfiox.core.uikit.components.slider.video.param.SliderVideoComponentPreviewParam
import su.anfiox.core.uikit.components.slider.video.param.SliderVideoComponentProvider
import su.anfiox.core.uikit.util.DefaultPreview
import su.anfiox.core.uikit.util.onUpdateShimmerBounds
import su.anifox.domain.model.anime.media.AnimeVideo
import su.anifox.domain.state.StateListWrapper

@Composable
fun SliderVideoComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    itemModifier: Modifier = Modifier.width(CardVideoLandscapeDefaults.Width.Default),
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    headerTitle: String,
    contentState: StateListWrapper<AnimeVideo>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentArrangement: Arrangement.Horizontal = CardVideoLandscapeDefaults.HorizontalArrangement.Default,
    onItemClick: (String) -> Unit,
    showCardMoreWhenPastLimit: Boolean = true,
    isTypeVisible: Boolean = true,
    onMoreClick: (() -> Unit)? = null,
) {
    // header
    if (contentState.isLoading) {
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
    LazyRow(
        modifier = modifier.onUpdateShimmerBounds(shimmer),
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement,
    ) {
        if (contentState.isLoading) {
            showCardVideoLandscapeShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if (contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it.playerUrl },
            ) { video ->
                CardVideoLandscape(
                    modifier = itemModifier,
                    data = video,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = {
                        onItemClick.invoke(video.playerUrl)
                    },
                    isTypeVisible = isTypeVisible,
                )
            }
            if (showCardMoreWhenPastLimit) {
                showCardVideoLandscapeMoreWhenPastLimit(
                    size = contentState.data.size,
                    onClick = {
                        onMoreClick?.invoke()
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScrollableHorizontalContentVideo(
    @PreviewParameter(SliderVideoComponentProvider::class) param: SliderVideoComponentPreviewParam,
) {
    DefaultPreview(true) {
        Column(
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SliderVideoComponent(
                modifier = param.modifier,
                headerModifier = param.headerModifier,
                itemModifier = param.itemModifier,
                thumbnailHeight = param.thumbnailHeight,
                thumbnailWidth = param.thumbnailWidth,
                headerTitle = param.headerTitle,
                contentState = param.contentState,
                contentPadding = param.contentPadding,
                contentArrangement = param.contentArrangement,
                onItemClick = param.onItemClick,
                onMoreClick = param.onMoreClick,
            )
        }
    }
}
