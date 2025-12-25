package su.anfiox.core.uikit.components.card.screenshot

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import su.anfiox.core.uikit.components.card.screenshot.param.CardScreenshotLandscapePreviewParam
import su.anfiox.core.uikit.components.card.screenshot.param.CardScreenshotLandscapeProvider
import su.anfiox.core.uikit.util.DefaultPreview

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
    Card(
        modifier = modifier
            .width(thumbnailWidth)
            .height(thumbnailHeight)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke() },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        )
    ) {
        // TODO: REWORK TO KMP
//        AsyncImage(
//            modifier = Modifier
//                .background(MaterialTheme.colorScheme.onSurfaceVariant)
//                .fillMaxSize()
//                .clip(MaterialTheme.shapes.medium),
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(image)
//                .crossfade(true)
//                .size(Size.ORIGINAL)
//                .build(),
//            contentDescription = "Content thumbnail",
//            contentScale = ContentScale.Crop,
//            onError = {
//                println(it.result.throwable.message)
//            },
//        )
    }
}

@Preview
@Composable
private fun PreviewCardScreenshotLandscapeDefault(
    @PreviewParameter(CardScreenshotLandscapeProvider::class)
    param: CardScreenshotLandscapePreviewParam,
) {
    DefaultPreview {
        CardScreenshotLandscape(
            modifier = param.modifier,
            image = param.image,
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            onClick = param.onClick,
        )
    }
}
