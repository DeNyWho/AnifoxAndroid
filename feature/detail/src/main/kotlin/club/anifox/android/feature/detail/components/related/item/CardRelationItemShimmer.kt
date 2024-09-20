package club.anifox.android.feature.detail.components.related.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.shimmer.ShimmerDefaults
import club.anifox.android.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
internal fun CardRelationItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = CardRelationItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardRelationItemDefaults.Width.Default,
) {
    Row (
        modifier = modifier
            .shimmer(shimmerInstance)
            .height(thumbnailHeight)
            .fillMaxWidth(),
    ) {
        Card(
            modifier = Modifier
                .width(thumbnailWidth)
                .fillMaxHeight(),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            shape = MaterialTheme.shapes.medium,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
        Column (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(ShimmerDefaults.Text.height)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(ShimmerDefaults.Text.height)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(ShimmerDefaults.Text.height)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
    }
}

@Composable
internal fun ColumnScope.ShowCardRelationItemShimmer(
    modifier: Modifier,
    shimmerInstance: Shimmer,
    count: Int = 3,
    thumbnailHeight: Dp = CardRelationItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardRelationItemDefaults.Width.Default,
) {
    repeat(count) {
        CardRelationItemShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
            thumbnailWidth = thumbnailWidth,
        )
    }
}


@PreviewLightDark
@Composable
private fun PreviewRelationItemShimmer() {
    DefaultPreview {
        CardRelationItemShimmer(
            Modifier,
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
