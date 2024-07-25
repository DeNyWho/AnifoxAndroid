package club.anifox.android.feature.search.composable.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.AnifoxChipPrimary
import club.anifox.android.domain.model.anime.AnimeLight
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun AnimeSearchItem(
    data: AnimeLight,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .width(120.dp)
                .height(170.dp)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current)
                .data(data.image)
                .crossfade(true)
                .size(Size.ORIGINAL)
                .build(),
            contentDescription = "Content thumbnail",
            contentScale = ContentScale.Crop,
            onError = {
                println(it.result.throwable.message)
            },
        )
        Column (
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier,
            )
            FlowRow (
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AnifoxChipPrimary(
                    title = data.year.toString(),
                )
                AnifoxChipPrimary(
                    title = data.type.toString(),
                )
                if(data.rating != null) {
                    AnifoxChipPrimary(
                        title = data.rating.toString(),
                    )
                }
            }
            Text(
                text = data.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier,
            )
        }
    }

}