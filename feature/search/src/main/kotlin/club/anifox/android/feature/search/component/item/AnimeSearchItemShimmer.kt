package club.anifox.android.feature.search.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.ChipShimmer
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LocalScreenInfo
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun AnimeSearchComponentItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = AnimeSearchComponentItemDefaults.Height.Medium,
    thumbnailWidth: Dp = AnimeSearchComponentItemDefaults.Width.Medium,
) {
    val screenInfo = LocalScreenInfo.current
    val textHeight = 24.dp + screenInfo.fontSizePrefs.fontSizeExtra.dp

    Row(
        modifier = modifier
            .shimmer(shimmerInstance),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(
            modifier = Modifier
                .width(thumbnailWidth)
                .height(thumbnailHeight),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp,
            ),
            shape = MaterialTheme.shapes.medium,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(100.dp)
                    .height(textHeight)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ChipShimmer(
                    shimmerInstance = shimmerInstance,
                )
                ChipShimmer(
                    shimmerInstance = shimmerInstance,
                )
            }
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(textHeight)
                    .padding(0.dp, 8.dp, 0.dp, 0.dp)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
    }
}

internal fun LazyGridScope.showAnimeSearchComponentItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    count: Int = 5,
    thumbnailHeight: Dp = AnimeSearchComponentItemDefaults.Height.Medium,
    thumbnailWidth: Dp = AnimeSearchComponentItemDefaults.Width.Medium,
) {
    items(count) {
        AnimeSearchComponentItemShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
            thumbnailWidth = thumbnailWidth,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAnimeSearchComponentItemShimmer() {
    DefaultPreview {
        AnimeSearchComponentItemShimmer(
            Modifier,
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
