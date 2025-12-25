package su.anfiox.core.uikit.components.card.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.components.shimmer.ShimmerDefaults
import su.anfiox.core.uikit.util.DefaultPreview

@Composable
fun CardCharactersItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
) {
    Column(
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

fun LazyListScope.showCardCharactersItemShimmer(
    modifier: Modifier = Modifier,
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

fun LazyGridScope.showCardCharactersItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    count: Int = 16,
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

@Preview
@Composable
private fun PreviewCardCharactersItemShimmer() {
    DefaultPreview {
        CardCharactersItemShimmer(
            Modifier,
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
