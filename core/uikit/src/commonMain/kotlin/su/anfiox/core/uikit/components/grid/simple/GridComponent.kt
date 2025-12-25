package su.anfiox.core.uikit.components.grid.simple

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import su.anfiox.core.uikit.components.card.anime.CardAnimePortrait
import su.anfiox.core.uikit.components.card.anime.CardAnimePortraitDefaults
import su.anfiox.core.uikit.components.card.anime.showCardAnimePortraitShimmer
import su.anfiox.core.uikit.components.grid.param.GridComponentPreviewParam
import su.anfiox.core.uikit.components.grid.param.GridComponentProvider
import su.anfiox.core.uikit.param.GlobalParams
import su.anfiox.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.flowOf
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import su.anifox.domain.model.anime.common.AnimeLight

@Composable
fun GridComponent(
    modifier: Modifier = Modifier,
    itemModifier: Modifier = Modifier.width(CardAnimePortraitDefaults.Width.GridSmall),
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.GridSmall,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.GridSmall,
    contentState: LazyPagingItems<AnimeLight>,
    horizontalContentArrangement: Arrangement.Horizontal = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
    verticalContentArrangement: Arrangement.Vertical = CardAnimePortraitDefaults.VerticalArrangement.Grid,
    onItemClick: (String) -> Unit,
    minColumnSize: Dp,
    state: LazyGridState = rememberLazyGridState(),
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Adaptive(minSize = minColumnSize),
        horizontalArrangement = horizontalContentArrangement,
        verticalArrangement = verticalContentArrangement,
        state = state,
    ) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(verticalContentArrangement.spacing))
        }

        if (contentState.loadState.refresh !is LoadState.Loading) {
            items(
                count = contentState.itemCount,
                key = contentState.itemKey { it.url },
            ) { index ->
                val item = contentState[index]
                if (item != null) {
                    CardAnimePortrait(
                        modifier = itemModifier,
                        data = item,
                        thumbnailHeight = thumbnailHeight,
                        thumbnailWidth = thumbnailWidth,
                        onClick = { onItemClick(item.url) },
                    )
                }
            }
        }

        when {
            contentState.loadState.refresh is LoadState.Loading -> {
                showCardAnimePortraitShimmer(
                    count = 12,
                    modifier = itemModifier,
                    shimmerInstance = shimmer,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                )
            }

            contentState.loadState.append is LoadState.Loading -> {
                showCardAnimePortraitShimmer(
                    count = 3,
                    modifier = itemModifier,
                    shimmerInstance = shimmer,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                )
            }

            contentState.loadState.append is LoadState.Error -> {

            }
        }

        item(span = { GridItemSpan(maxLineSpan) }) {
            Spacer(modifier = Modifier.height(verticalContentArrangement.spacing))
        }
    }
}

@Preview
@Composable
private fun PreviewGridContent(
    @PreviewParameter(GridComponentProvider::class) param: GridComponentPreviewParam,
) {
    val lazyPagingItems = flowOf(
        PagingData.from(GlobalParams.DataSetAnimeLight)
    ).collectAsLazyPagingItems()

    DefaultPreview(true) {
        GridComponent(
            modifier = param.modifier,
            itemModifier = param.itemModifier,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            contentState = lazyPagingItems,
            horizontalContentArrangement = param.contentArrangement,
            onItemClick = param.onItemClick,
            minColumnSize = param.minColumnSize,
        )
    }
}
