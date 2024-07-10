package club.anifox.android.core.uikit.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

@Stable
fun Modifier.onUpdateShimmerBounds(
    shimmerInstance: Shimmer,
) = this.then(
    onGloballyPositioned { value: LayoutCoordinates ->
        val position = value.unclippedBoundsInWindow()
        shimmerInstance.updateBounds(position)
    },
)
