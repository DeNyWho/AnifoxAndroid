package club.anifox.android.commonui.component.slider.content

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.commonui.component.card.CardAnimePortrait
import club.anifox.android.commonui.component.card.CardAnimePortraitDefaults
import club.anifox.android.commonui.component.card.showCardAnimePortraitShimmer
import club.anifox.android.commonui.component.slider.content.param.SliderContentPreviewParam
import club.anifox.android.commonui.component.slider.content.param.SliderContentProvider
import club.anifox.android.commonui.component.slider.header.SliderHeader
import club.anifox.android.commonui.component.slider.header.SliderHeaderShimmer
import club.anifox.android.commonui.theme.AnifoxTheme
import club.anifox.android.commonui.util.onUpdateShimmerBounds
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun SliderContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.Default,
    itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.Default),
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    headerTitle: String,
    contentState: StateListWrapper<AnimeLight>,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    contentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Default,
    textAlign: TextAlign = TextAlign.Start,
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
            showCardAnimePortraitShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
            )
        } else if(contentState.data.isNotEmpty()) {
            items(
                contentState.data,
                key = { it.url },
            ) { data ->
                CardAnimePortrait(
                    modifier = itemModifier,
                    data = data,
                    thumbnailHeight = thumbnailHeight,
                    textAlign = textAlign,
                    onClick = { onItemClick.invoke(data.url) },
                )
            }
        }
    }

}

@PreviewLightDark
@Composable
private fun PreviewScrollableHorizontalContentDefault(
    @PreviewParameter(SliderContentProvider::class) param: SliderContentPreviewParam,
) {
    AnifoxTheme {
        Column (
            Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            SliderContent (
                modifier = param.modifier,
                headerModifier = param.headerModifier,
                itemModifier = param.itemModifier,
                thumbnailHeight = param.thumbnailHeight,
                headerTitle = param.headerTitle,
                contentState = param.contentState,
                contentPadding = param.contentPadding,
                contentArrangement = param.contentArrangement,
                textAlign = param.textAlign,
                onItemClick = param.onItemClick,
            )
        }
    }
}
