package club.anifox.android.feature.search.component.item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.AnifoxChipPrimary
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.feature.search.R
import club.anifox.android.feature.search.component.item.param.AnimeSearchComponentItemPreviewParam
import club.anifox.android.feature.search.component.item.param.AnimeSearchComponentItemProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun AnimeSearchComponentItem(
    thumbnailHeight: Dp,
    thumbnailWidth: Dp,
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
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .fillMaxSize()
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
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = data.title,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier,
            )
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                AnifoxChipPrimary(
                    title = data.year.toString(),
                )
                AnifoxChipPrimary(
                    title = data.type.toString(),
                )
                if (data.episodes > 1) {
                    AnifoxChipPrimary(
                        title = "${data.episodes} ${stringResource(R.string.feature_search_item_episodes)}",
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

@Preview(showBackground = true)
@Composable
private fun PreviewAnimeSearchComponentItemShimmer(
    @PreviewParameter(AnimeSearchComponentItemProvider::class)
    param: AnimeSearchComponentItemPreviewParam,
) {
    DefaultPreview {
        AnimeSearchComponentItem(
            thumbnailHeight = param.thumbnailHeight,
            thumbnailWidth = param.thumbnailWidth,
            data = param.data,
            onClick = param.onClick,
        )
    }
}