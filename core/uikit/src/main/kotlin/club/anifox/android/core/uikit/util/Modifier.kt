package club.anifox.android.core.uikit.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.unclippedBoundsInWindow

fun Modifier.clickableWithoutRipple(enabled: Boolean = true, onClick: () -> Unit): Modifier = composed {
    this.then(
        if (enabled) {
            clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
        } else {
            this
        }
    )
}

@Stable
fun Modifier.onUpdateShimmerBounds(
    shimmerInstance: Shimmer,
) = this.then(
    Modifier.onGloballyPositioned { value: LayoutCoordinates ->
        val position = value.unclippedBoundsInWindow()
        shimmerInstance.updateBounds(position)
    }
)
