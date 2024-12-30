package club.anifox.android.feature.detail.components.characters.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
internal fun CardCharactersItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
) {
    Column (
        modifier = modifier
            .shimmer(shimmerInstance),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .width(thumbnailWidth)
                .height(thumbnailHeight),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            shape = CircleShape,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
            )
        }
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(top = 4.dp)
                .height(ShimmerDefaults.Text.height)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
        Box(
            modifier = Modifier
                .width(120.dp)
                .padding(top = 4.dp)
                .height(ShimmerDefaults.Text.height)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

internal fun LazyListScope.showCardCharactersItemShimmer(
    modifier: Modifier,
    shimmerInstance: Shimmer,
    count: Int = 12,
    thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
) {
    items(count) {
        CardCharactersItemShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
            thumbnailWidth = thumbnailWidth,
        )
    }
}


@PreviewLightDark
@Composable
private fun PreviewCardCharactersItemShimmer() {
    DefaultPreview {
        CardCharactersItemShimmer(
            Modifier,
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
