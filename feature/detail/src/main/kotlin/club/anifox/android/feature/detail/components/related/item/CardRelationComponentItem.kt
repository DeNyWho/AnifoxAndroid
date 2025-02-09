package club.anifox.android.feature.detail.components.related.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import club.anifox.android.domain.model.anime.related.AnimeRelatedLight
import club.anifox.android.feature.detail.components.related.item.param.CardRelationComponentItemPreviewParam
import club.anifox.android.feature.detail.components.related.item.param.CardRelationComponentItemProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CardRelationComponentItem(
    modifier: Modifier,
    data: AnimeRelatedLight,
    thumbnailHeight: Dp = CardRelationComponentItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardRelationComponentItemDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(thumbnailHeight)
            .clickable {
                onClick.invoke()
            }
            .fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(thumbnailWidth),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            shape = MaterialTheme.shapes.medium,
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.anime.image)
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
        Column (
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = data.anime.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier,
            )
            Text(
                text = data.type,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier,
            )
            FlowRow (
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AnifoxChipPrimary(
                    title = data.anime.year.toString(),
                )
                AnifoxChipPrimary(
                    title = data.anime.type.toString(),
                )
                if(data.anime.rating != null) {
                    AnifoxChipPrimary(
                        title = data.anime.rating.toString(),
                    )
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardRelationComponentItemDefault(
    @PreviewParameter(CardRelationComponentItemProvider::class) param: CardRelationComponentItemPreviewParam,
) {
    DefaultPreview {
        CardRelationComponentItem (
            modifier = param.modifier,
            data = param.data,
            thumbnailHeight = param.thumbnailHeight,
            onClick = param.onClick,
        )
    }
}