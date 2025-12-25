package su.anfiox.core.uikit.components.card.image

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CardSimpleImageLandscape(
    modifier: Modifier = Modifier,
    image: String,
    onClick: (String) -> Unit,
    ratio: Float,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(ratio)
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick.invoke(image) },
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