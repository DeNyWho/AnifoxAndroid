package club.anifox.android.core.uikit.component.card.video

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CardVideoLandscapeShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
) {
    Column(
        modifier = modifier
            .padding(bottom = 8.dp)
            .shimmer(shimmerInstance),
    ) {
        Card(
            modifier = Modifier
                .width(thumbnailWidth)
                .height(thumbnailHeight)
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
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(104.dp)
                .height(24.dp)
                .padding(0.dp, 4.dp, 0.dp, 0.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(62.dp)
                .height(24.dp)
                .padding(0.dp, 4.dp, 0.dp, 0.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

fun LazyListScope.showCardVideoLandscapeShimmer(
    modifier: Modifier,
    shimmerInstance: Shimmer,
    count: Int = 11,
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
) {
    items(count) {
        CardVideoLandscapeShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
            thumbnailWidth = thumbnailWidth,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardVideoLandscapeShimmer() {
    DefaultPreview {
        CardVideoLandscapeShimmer(
            Modifier.width(CardVideoLandscapeDefaults.Width.Default),
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
