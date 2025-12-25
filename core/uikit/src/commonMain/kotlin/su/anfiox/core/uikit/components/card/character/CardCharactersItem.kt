package su.anfiox.core.uikit.components.card.character

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import su.anfiox.core.uikit.components.card.character.param.CardCharactersItemPreviewParam
import su.anfiox.core.uikit.components.card.character.param.CardCharactersItemProvider
import su.anfiox.core.uikit.util.DefaultPreview
import su.anfiox.core.uikit.util.clickableWithoutRipple
import su.anfiox.core.uikit.util.limitTo
import su.anifox.domain.model.characters.AnimeCharactersLight

@Composable
fun CardCharactersItem(
    modifier: Modifier = Modifier,
    data: AnimeCharactersLight,
    thumbnailHeight: Dp = CardCharactersItemDefaults.Height.Default,
    thumbnailWidth: Dp = CardCharactersItemDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Column(
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
            // TODO: REWORK TO KMP
//            AsyncImage(
//                modifier = Modifier
//                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
//                    .fillMaxSize()
//                    .clip(CircleShape),
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(data.image)
//                    .crossfade(true)
//                    .size(Size.ORIGINAL)
//                    .build(),
//                contentDescription = "Content thumbnail",
//                contentScale = ContentScale.Crop,
//                onError = {
//                    println(it.result.throwable.message)
//                },
//            )
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
            text = data.role.name,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 4.dp),
        )
    }
}

@Preview
@Composable
private fun PreviewCardCharactersItemDefault(
    @PreviewParameter(CardCharactersItemProvider::class) param: CardCharactersItemPreviewParam,
) {
    DefaultPreview {
        CardCharactersItem(
            modifier = param.modifier,
            data = param.data,
            thumbnailHeight = param.thumbnailHeight,
            onClick = param.onClick,
        )
    }
}