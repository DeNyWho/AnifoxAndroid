package su.anfiox.core.uikit.components.slider.simple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import su.anfiox.core.uikit.components.card.anime.CardAnimePortraitDefaults
import su.anfiox.core.uikit.components.card.anime.showCardAnimePortraitMoreWhenPastLimit
import su.anfiox.core.uikit.components.card.anime.showCardAnimePortraitShimmer
import su.anfiox.core.uikit.components.slider.SliderComponentDefaults
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import su.anfiox.core.uikit.components.card.anime.CardAnimePortrait
import su.anfiox.core.uikit.components.slider.header.SliderHeader
import su.anfiox.core.uikit.components.slider.header.SliderHeaderShimmer
import su.anfiox.core.uikit.components.slider.simple.param.SliderComponentPreviewParam
import su.anfiox.core.uikit.components.slider.simple.param.SliderComponentProvider
import su.anfiox.core.uikit.util.DefaultPreview
import su.anifox.domain.model.anime.common.AnimeLight
import su.anifox.domain.state.StateListWrapper

@Composable
fun SliderComponent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderComponentDefaults.BottomOnly,
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
    headerTitle: String,
    contentState: StateListWrapper<AnimeLight>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    contentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Default,
    textAlign: TextAlign = TextAlign.Start,
    onItemClick: (String) -> Unit,
    isMoreVisible: Boolean = false,
    isMorePastLimitVisible: Boolean = false,
    onMoreClick: () -> Unit = { },
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
            isMoreVisible = isMoreVisible,
            onMoreClick = onMoreClick,
        )
    }

    // content
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = contentArrangement,
    ) {
        if (contentState.isLoading) {
            showCardAnimePortraitShimmer(
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if (contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it.url },
            ) { data ->
                CardAnimePortrait(
                    data = data,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    textAlign = textAlign,
                    onClick = { onItemClick.invoke(data.url) },
                )
            }
            if (isMorePastLimitVisible) {
                showCardAnimePortraitMoreWhenPastLimit(
                    size = contentState.data.size,
                    onClick = {
                        onMoreClick.invoke()
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewScrollableHorizontalContentDefault(
    @PreviewParameter(SliderComponentProvider::class) param: SliderComponentPreviewParam,
) {
    DefaultPreview(true) {
        SliderComponent(
            modifier = param.modifier,
            headerModifier = param.headerModifier,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            headerTitle = param.headerTitle,
            contentState = param.contentState,
            contentPadding = param.contentPadding,
            contentArrangement = param.contentArrangement,
            textAlign = param.textAlign,
            onItemClick = param.onItemClick,
            isMoreVisible = param.isMoreVisible,
        )
    }
}
