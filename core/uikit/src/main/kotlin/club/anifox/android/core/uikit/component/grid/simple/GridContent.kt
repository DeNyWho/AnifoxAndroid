package club.anifox.android.core.uikit.component.grid.simple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.card.anime.showCardAnimePortraitShimmer
import club.anifox.android.core.uikit.component.grid.param.GridContentPreviewParam
import club.anifox.android.core.uikit.component.grid.param.GridContentProvider
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer

@Composable
fun GridContent(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.GridSmall),
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.Custom),
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.GridSmall,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.GridSmall,
    contentState: StateListWrapper<AnimeLight>,
    horizontalContentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
    verticalContentArrangement: Arrangement.Vertical = CardAnimePortraitDefaults.VerticalArrangement.Grid,
    onItemClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize(),
        columns = GridCells.Adaptive(minSize = CardAnimePortraitDefaults.Width.GridSmall),
        horizontalArrangement = horizontalContentArrangement,
        verticalArrangement = verticalContentArrangement,
    ) {
        if(contentState.isLoading) {
            showCardAnimePortraitShimmer(
                modifier = itemModifier,
                shimmerInstance = shimmer,
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
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
                    thumbnailWidth = thumbnailWidth,
                    onClick = { onItemClick.invoke(data.url) },
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGridContent(
    @PreviewParameter(GridContentProvider::class) param: GridContentPreviewParam,
) {
    DefaultPreview(true) {
        GridContent(
            modifier = param.modifier,
            itemModifier = param.itemModifier,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            contentState = param.contentState,
            horizontalContentArrangement = param.contentArrangement,
            onItemClick = param.onItemClick,
        )
    }
}
