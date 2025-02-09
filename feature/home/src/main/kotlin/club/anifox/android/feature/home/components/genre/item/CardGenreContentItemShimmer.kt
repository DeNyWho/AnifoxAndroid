package club.anifox.android.feature.home.components.genre.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.shimmer.ShimmerDefaults
import club.anifox.android.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds.Custom
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
internal fun CardGenreComponentItemShimmer(
    shimmerInstance: Shimmer,
) {
    Card(
        modifier = Modifier.shimmer(shimmerInstance),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Box(
            modifier = Modifier
                .width(160.dp)
                .padding(28.dp)
                .height(ShimmerDefaults.Text.height)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

internal fun LazyListScope.showCardGenreComponentItemShimmer(
    shimmerInstance: Shimmer,
    count: Int = 11,
) {
    items(count) {
        CardGenreComponentItemShimmer(
            shimmerInstance = shimmerInstance,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardGenreComponentItemShimmer() {
    DefaultPreview {
        CardGenreComponentItemShimmer(rememberShimmer(shimmerBounds = Custom))
    }
}