package club.anifox.android.core.uikit.component.card.video

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import club.anifox.android.domain.model.anime.videos.AnimeVideosLight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

/**
 * Anifox CardVideoLandscape (anime video card) [CardVideoLandscape].
 *
 * @param modifier Modifier.
 * @param data AnimeVideosLight.
 * @param thumbnailHeight The height of the anime video card.
 * @param thumbnailWidth The width of the anime video card.
 * @param onClick Will be called when the user clicks on the anime video card.
 */
@Composable
fun CardVideoLandscape(
    modifier: Modifier = Modifier,
    data: AnimeVideosLight,
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(thumbnailWidth),
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
                    .data(data.imageUrl)
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
        Text(
            text = data.name,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Text(
            text = data.type.toString(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
    }
}