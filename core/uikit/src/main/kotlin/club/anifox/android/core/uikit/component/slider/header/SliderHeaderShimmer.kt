package club.anifox.android.core.uikit.component.slider.header

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun SliderHeaderShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer = rememberShimmer(ShimmerBounds.Custom),
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .heightIn(24.dp)
            .shimmer(shimmerInstance),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Box(
            modifier = Modifier
                .width(110.dp)
                .height(24.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@PreviewLightDark
@Composable
private fun SliderHeaderShimmerPreview(){
    SliderHeaderShimmer()
}