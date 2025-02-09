package club.anifox.android.feature.schedule.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.AnifoxChipPrimary
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.feature.schedule.components.item.param.AnimeScheduleComponentItemPreviewParam
import club.anifox.android.feature.schedule.components.item.param.AnimeScheduleComponentProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
internal fun AnimeScheduleComponentItem(
    thumbnailHeight: Dp = AnimeScheduleComponentItemDefaults.Height.Small,
    thumbnailWidth: Dp = AnimeScheduleComponentItemDefaults.Width.Small,
    data: AnimeLight,
    onClick: (String) -> Unit,
) {
    Row(
        modifier = Modifier.clickable {
            onClick.invoke(data.url)
        },
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Card(
            modifier = Modifier
                .width(thumbnailWidth)
                .height(thumbnailHeight),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 4.dp,
            ),
            shape = MaterialTheme.shapes.medium,
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
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
        }

        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium,
            )

            AnifoxChipPrimary(
                title = "${data.episodesAired} из ${if(data.episodes != 0) data.episodes else "?"}"
            )

            Text(
                text = data.description,
                overflow = TextOverflow.Ellipsis,
                maxLines = 4,
                color = MaterialTheme.colorScheme.onBackground.copy(0.7f),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewAnimeScheduleItem(
    @PreviewParameter(AnimeScheduleComponentProvider::class) param: AnimeScheduleComponentItemPreviewParam,
) {
    DefaultPreview {
        AnimeScheduleComponentItem(
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            data = param.data,
            onClick = param.onClick,
        )
    }
}