package club.anifox.android.feature.screenshots.component.grid.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
internal fun CardScreenshotGridComponentItem(
    modifier: Modifier = Modifier,
    image: String,
    onClick: (String) -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16 / 9f)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke(image) },
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp,
        )
    ) {
        AsyncImage(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onSurfaceVariant)
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