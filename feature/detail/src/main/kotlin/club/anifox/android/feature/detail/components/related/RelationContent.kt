package club.anifox.android.feature.detail.components.related

import androidx.compose.foundation.layout.Arrangement.Vertical
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import club.anifox.android.commonui.component.slider.SliderContentDefaults
import club.anifox.android.commonui.component.slider.header.SliderHeader
import club.anifox.android.commonui.component.slider.header.SliderHeaderShimmer
import club.anifox.android.commonui.util.onUpdateShimmerBounds
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.detail.components.related.item.CardRelationItem
import club.anifox.android.feature.detail.components.related.item.CardRelationItemDefaults.Height
import club.anifox.android.feature.detail.components.related.item.CardRelationItemDefaults.VerticalArrangement
import club.anifox.android.feature.detail.components.related.item.CardRelationItemDefaults.Width
import club.anifox.android.feature.detail.components.related.item.ShowCardRelationItemShimmer
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds.Custom
import com.valentinilk.shimmer.rememberShimmer

@Composable
internal fun RelationContent(
    modifier: Modifier = Modifier,
    headerModifier: Modifier = SliderContentDefaults.Default,
    itemModifier: Modifier = Modifier,
    shimmer: Shimmer = rememberShimmer(Custom),
    thumbnailHeight: Dp = Height.Default,
    thumbnailWidth: Dp = Width.Default,
    headerTitle: String,
    contentState: StateListWrapper<AnimeRelatedLight>,
    contentArrangement: Vertical = VerticalArrangement.Default,
    onItemClick: (String) -> Unit,
    countContent: Int,
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
    Column(
        modifier = modifier.onUpdateShimmerBounds(shimmer),
        verticalArrangement = contentArrangement,
    ) {
        if(contentState.isLoading) {
            ShowCardRelationItemShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
            )
        } else if(contentState.data.isNotEmpty()) {
            contentState.data.take(countContent).forEach { data ->
                CardRelationItem(
                    modifier = itemModifier,
                    data = data,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = { onItemClick.invoke(data.anime.url) }
                )
            }
        }
    }
}