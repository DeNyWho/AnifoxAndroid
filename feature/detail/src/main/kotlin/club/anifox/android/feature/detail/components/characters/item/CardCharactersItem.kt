package club.anifox.android.feature.detail.components.characters.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.core.uikit.util.limitTo
import club.anifox.android.domain.model.anime.characters.AnimeCharactersLight
import club.anifox.android.feature.detail.components.characters.item.param.CardCharactersItemPreviewParam
import club.anifox.android.feature.detail.components.characters.item.param.CardCharactersItemProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

@Composable
internal fun CardCharactersItem(
    modifier: Modifier,
    data: AnimeCharactersLight,
    thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Column (
        modifier = modifier
            .clickableWithoutRipple {
                onClick.invoke()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            modifier = Modifier
                .width(thumbnailWidth)
                .height(thumbnailHeight),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            shape = CircleShape,
        ) {
            AsyncImage(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
                    .fillMaxSize()
                    .clip(CircleShape),
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

        Text(
            text = data.name.limitTo(12),
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            maxLines = 1,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier
                .padding(top = 4.dp),
        )

        Text(
            text = data.role,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 4.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardCharactersItemDefault(
    @PreviewParameter(CardCharactersItemProvider::class) param: CardCharactersItemPreviewParam,
) {
    DefaultPreview {
        CardCharactersItem (
            modifier = param.modifier,
            data = param.data,
            thumbnailHeight = param.thumbnailHeight,
            onClick = param.onClick,
        )
    }
}