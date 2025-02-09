package club.anifox.android.core.uikit.component.grid.simple

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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.card.anime.showCardAnimePortraitShimmer
import club.anifox.android.core.uikit.component.grid.param.GridComponentPreviewParam
import club.anifox.android.core.uikit.component.grid.param.GridComponentProvider
import club.anifox.android.core.uikit.param.GlobalParams
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.AnimeLight
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.flowOf

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

        items(
            count = contentState.itemCount,
            key = contentState.itemKey { it.url },
        ) { index ->
            val item = contentState[index]
            item?.let {
                CardAnimePortrait(
                    modifier = itemModifier,
                    data = it,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                    onClick = { onItemClick.invoke(it.url) },
                )
            }
        }

        when {
            contentState.loadState.append is LoadState.Loading -> {
                showCardAnimePortraitShimmer(
                    modifier = itemModifier,
                    shimmerInstance = shimmer,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                )
            }
            contentState.loadState.refresh is LoadState.Loading -> {
                showCardAnimePortraitShimmer(
                    modifier = itemModifier,
                    shimmerInstance = shimmer,
                    thumbnailHeight = thumbnailHeight,
                    thumbnailWidth = thumbnailWidth,
                )
            }
            contentState.loadState.append is LoadState.Error -> {

            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewGridContent(
    @PreviewParameter(GridComponentProvider::class) param: GridComponentPreviewParam,
) {
    val lazyPagingItems = flowOf(PagingData.from(GlobalParams.DataSetAnimeLight)).collectAsLazyPagingItems()

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
