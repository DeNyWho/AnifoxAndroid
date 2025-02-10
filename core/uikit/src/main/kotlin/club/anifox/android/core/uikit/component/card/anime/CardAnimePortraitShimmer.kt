package club.anifox.android.core.uikit.component.card.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LocalScreenInfo
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CardThumbnailPortraitShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
) {
    val screenInfo = LocalScreenInfo.current
    val textHeight = 24.dp + screenInfo.fontSizePrefs.fontSizeExtra.dp

    Column(
        modifier = modifier
            .width(thumbnailWidth)
            .height(thumbnailHeight + 50.dp)
            .shimmer(shimmerInstance),
    ) {
        Card(
            modifier = Modifier
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
                .fillMaxWidth()
                .height(textHeight)
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )

        Box(
            modifier = Modifier
                .width(62.dp)
                .height(textHeight)
                .padding(0.dp, 8.dp, 0.dp, 0.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

fun LazyListScope.showCardAnimePortraitShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    count: Int = 5,
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
) {
    items(count) {
        CardThumbnailPortraitShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
            thumbnailWidth = thumbnailWidth,
        )
    }
}

fun LazyGridScope.showCardAnimePortraitShimmer(
    modifier: Modifier,
    shimmerInstance: Shimmer,
    count: Int = 9,
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.GridSmall,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.GridSmall,
) {
    items(count) {
        CardThumbnailPortraitShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
            thumbnailWidth = thumbnailWidth,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardAnimePortraitShimmer() {
    DefaultPreview {
        CardThumbnailPortraitShimmer(
            Modifier,
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
