package club.anifox.android.core.uikit.component.card.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.card.anime.param.CardAnimePreviewParam
import club.anifox.android.core.uikit.component.card.anime.param.CardAnimeProvider
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.domain.model.anime.AnimeLight
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Anifox CardAnimePortrait (anime card with text) [CardAnimePortrait].
 *
 * @param modifier Modifier.
 * @param data data in the form of AnimeLight.
 * @param thumbnailHeight The height of the anime card.
 * @param thumbnailWidth The width of the anime card.
 * @param textAlign TextAlign.
 * @param onClick Will be called when the user clicks on the anime card.
 */
@Composable
fun CardAnimePortrait(
    modifier: Modifier = Modifier,
    data: AnimeLight,
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
    textAlign: TextAlign = TextAlign.Start,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .width(thumbnailWidth)
            .height(thumbnailHeight + 50.dp)
            .clickable { onClick.invoke() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
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
                    .build(),
                contentDescription = "Content thumbnail",
                contentScale = ContentScale.Crop,
                onError = {
                    println(it.result.throwable.message)
                },
            )
        }

        Text(
            text = data.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground.copy(0.8f),
            textAlign = textAlign,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardThumbnailPortraitDefault(
    @PreviewParameter(CardAnimeProvider::class) param: CardAnimePreviewParam,
) {
    DefaultPreview {
        CardAnimePortrait (
            modifier = param.modifier,
            data = param.data,
            thumbnailHeight = param.thumbnailHeight,
            onClick = { },
        )
    }
}
