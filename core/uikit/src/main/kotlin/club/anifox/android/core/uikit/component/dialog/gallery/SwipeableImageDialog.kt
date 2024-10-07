package club.anifox.android.core.uikit.component.dialog.gallery

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun SwipeableImageDialog(
    images: List<String>,
    initialIndex: Int = 0,
    onDismiss: () -> Unit = { },
) {
    var currentIndex by remember { mutableIntStateOf(initialIndex) }
    var offsetY by remember { mutableFloatStateOf(0f) }
    var offsetX by remember { mutableFloatStateOf(0f) }
    var zoomScale by remember { mutableFloatStateOf(1f) }
    var imageSize by remember { mutableStateOf(IntSize.Zero) }

    val scope = rememberCoroutineScope()

    val backgroundAlpha by animateFloatAsState(
        targetValue = if (zoomScale > 1f) 1f else (1f - (offsetY.absoluteValue / 300f).coerceIn(0f, 1f)),
        label = "",
    )

    // Thresholds for changing the image and closing
    val swipeThresholdX = 300f
    val swipeThresholdY = 300f

    fun constrainOffset(offset: Offset): Offset {
        val maxX = (imageSize.width * (zoomScale - 1) / 2f).coerceAtLeast(0f)
        val maxY = (imageSize.height * (zoomScale - 1) / 2f).coerceAtLeast(0f)
        return Offset(
            x = offset.x.coerceIn(-maxX, maxX),
            y = offset.y.coerceIn(-maxY, maxY),
        )
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Black.copy(alpha = backgroundAlpha),
        ) {
            AsyncImage(
                model = images[currentIndex],
                contentDescription = "Full screen image",
                modifier = Modifier
                    .fillMaxSize()
                    .onSizeChanged { imageSize = it }
                    // The offset will only be applied by Y if the image is zoomed in
                    .offset { IntOffset(if (zoomScale > 1f) offsetX.roundToInt() else 0, offsetY.roundToInt()) }
                    .graphicsLayer(
                        scaleX = zoomScale,
                        scaleY = zoomScale,
                    )
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            // use panning only when zoom in
                            if (zoomScale > 1f || zoom > 1f) {
                                zoomScale = (zoomScale * zoom).coerceIn(1f, 5f)
                                if (zoomScale > 1f) {
                                    val offset = constrainOffset(Offset(offsetX + pan.x, offsetY + pan.y))
                                    offsetX = offset.x
                                    offsetY = offset.y
                                } else {
                                    // Reset the offset if the zoom is <= 1
                                    offsetX = 0f
                                    offsetY = 0f
                                }
                            } else {
                                // Swipe horizontally to change the image, and vertically to close
                                offsetX += pan.x

                                // The Y offset is taken into account only for vertical swipes
                                if (pan.x.absoluteValue < pan.y.absoluteValue) {
                                    offsetY += pan.y
                                }
                            }
                        }
                    },
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
            )
        }
    }

    LaunchedEffect(offsetY, offsetX, zoomScale) {
        if (zoomScale <= 1f) {
            if (offsetY.absoluteValue > swipeThresholdY) {
                onDismiss()
            }

            else if (offsetX.absoluteValue > swipeThresholdX) {
                val nextIndex = if (offsetX > 0) {
                    (currentIndex - 1 + images.size) % images.size
                } else {
                    (currentIndex + 1) % images.size
                }
                currentIndex = nextIndex
                offsetX = 0f
                offsetY = 0f
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            scope.launch {
                zoomScale = 1f
                offsetX = 0f
                offsetY = 0f
            }
        }
    }
}