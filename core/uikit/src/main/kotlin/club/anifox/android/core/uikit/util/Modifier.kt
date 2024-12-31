package club.anifox.android.core.uikit.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

@Composable
fun Modifier.toolbarShadow(
    shadowElevation: Dp,
    tonalElevation: Dp,
    shape: Shape,
): Modifier {
    return this
        .shadow(
            elevation = 4.dp,
            shape = shape,
            clip = false,
        )
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp))
}