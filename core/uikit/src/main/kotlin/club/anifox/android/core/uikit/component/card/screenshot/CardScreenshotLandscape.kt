package club.anifox.android.core.uikit.component.card.screenshot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import club.anifox.android.core.uikit.component.card.screenshot.CardScreenshotLandscapeDefaults
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

/**
 * Anifox CardScreenshotLandscape (anime screenshot card) [CardScreenshotLandscape].
 *
 * @param modifier Modifier.
 * @param image Image url.
 * @param thumbnailHeight The height of the anime screenshot card.
 * @param thumbnailWidth The width of the anime screenshot card.
 * @param onClick Will be called when the user clicks on the anime screenshot card.
 */
@Composable
fun CardScreenshotLandscape(
    modifier: Modifier = Modifier,
    image: String,
    thumbnailHeight: Dp = CardScreenshotLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardScreenshotLandscapeDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .width(thumbnailWidth)
            .height(thumbnailHeight)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke() }
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current)
                .data(image)
                .crossfade(true)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = "Content thumbnail",
            contentScale = ContentScale.Crop,
            onError = {
                println(it.result.throwable.message)
            },
        )
    }
}