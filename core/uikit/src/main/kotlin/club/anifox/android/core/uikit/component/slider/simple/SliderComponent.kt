package club.anifox.android.core.uikit.component.slider.simple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.card.anime.showCardAnimePortraitMoreWhenPastLimit
import club.anifox.android.core.uikit.component.card.anime.showCardAnimePortraitShimmer
import club.anifox.android.core.uikit.component.slider.SliderComponentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.component.slider.simple.param.SliderComponentPreviewParam
import club.anifox.android.core.uikit.component.slider.simple.param.SliderComponentProvider
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

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

@PreviewLightDark
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
