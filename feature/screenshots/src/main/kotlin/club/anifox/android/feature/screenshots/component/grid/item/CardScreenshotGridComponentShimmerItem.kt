package club.anifox.android.feature.screenshots.component.grid.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import club.anifox.android.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
internal fun CardScreenshotGridComponentShimmerItem(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
) {
    Column(
        modifier = modifier
            .shimmer(shimmerInstance),
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .clip(MaterialTheme.shapes.medium),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            shape = MaterialTheme.shapes.small,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
    }
}

internal fun LazyGridScope.showCardScreenshotGridIComponentItemShimmer(
    shimmerInstance: Shimmer,
    count: Int = 5,
) {
    items(count) {
        CardScreenshotGridComponentShimmerItem(
            shimmerInstance = shimmerInstance,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardScreenshotGridComponentShimmerItem() {
    DefaultPreview {
        CardScreenshotGridComponentShimmerItem(
            Modifier.width(CardScreenshotLandscapeDefaults.Width.Default),
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
