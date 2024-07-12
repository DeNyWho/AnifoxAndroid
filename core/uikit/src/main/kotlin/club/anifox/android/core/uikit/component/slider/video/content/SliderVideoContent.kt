package club.anifox.android.core.uikit.component.slider.video.content

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.video.CardVideoLandscape
import club.anifox.android.core.uikit.component.card.video.CardVideoLandscapeDefaults
import club.anifox.android.core.uikit.component.card.video.showCardVideoLandscapeMoreWhenPastLimit
import club.anifox.android.core.uikit.component.card.video.showCardVideoLandscapeShimmer
import club.anifox.android.core.uikit.component.slider.SliderContentDefaults
import club.anifox.android.core.uikit.component.slider.header.SliderHeader
import club.anifox.android.core.uikit.component.slider.header.SliderHeaderShimmer
import club.anifox.android.core.uikit.component.slider.video.content.param.SliderVideoContentPreviewParam
import club.anifox.android.core.uikit.component.slider.video.content.param.SliderVideoContentProvider
import club.anifox.android.core.uikit.theme.AnifoxTheme
import club.anifox.android.core.uikit.util.onUpdateShimmerBounds
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun SliderVideoContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.BottomOnly,
    itemModifier: Modifier = Modifier.width(CardVideoLandscapeDefaults.Width.Default),
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    headerTitle: String,
    contentState: StateListWrapper<AnimeVideosLight>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    contentArrangement: Arrangement.Horizontal = CardVideoLandscapeDefaults.HorizontalArrangement.Default,
    onItemClick: (String) -> Unit,
) {
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
            showCardVideoLandscapeShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if(contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it.playerUrl },
            ) { video ->
                CardVideoLandscape(
                    modifier = itemModifier,
                    data = video,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = { },
                )
            }
            showCardVideoLandscapeMoreWhenPastLimit(
                size = contentState.data.size,
                onClick = { },
            )
        }
    }

}

@PreviewLightDark
@Composable
private fun PreviewScrollableHorizontalContentVideo(
    @PreviewParameter(SliderVideoContentProvider::class) param: SliderVideoContentPreviewParam,
) {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SliderVideoContent (
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
            )
        }
    }
}
