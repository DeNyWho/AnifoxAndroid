package club.anifox.android.commonui.component.card.screenshot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.commonui.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.commonui.theme.AnifoxTheme
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CardThumbnailPortraitShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
    thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
) {
    Column(
        modifier = modifier
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
    }
}

fun LazyListScope.showCardScreenshotLandscapeShimmer(
    modifier: Modifier,
    shimmerInstance: Shimmer,
    count: Int = 11,
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
) {
    items(count) {
        CardThumbnailPortraitShimmer(
            modifier = modifier,
            shimmerInstance = shimmerInstance,
            thumbnailHeight = thumbnailHeight,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardAnimePortraitShimmer() {
    AnifoxTheme {
        CardThumbnailPortraitShimmer(
            Modifier.width(CardAnimePortraitDefaults.Width.Default),
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}
