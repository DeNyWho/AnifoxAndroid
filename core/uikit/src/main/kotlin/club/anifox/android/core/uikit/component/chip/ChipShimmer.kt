package club.anifox.android.core.uikit.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun ChipShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer,
) {
    Surface(
        modifier = modifier
            .shimmer(shimmerInstance),
        shape = MaterialTheme.shapes.large,
        contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    ) {
        Box(
            modifier = Modifier
                .width(44.dp)
                .height(18.dp)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewAnifoxChipPrimary() {
    DefaultPreview {
        ChipShimmer(
            Modifier,
            rememberShimmer(shimmerBounds = ShimmerBounds.Custom),
        )
    }
}