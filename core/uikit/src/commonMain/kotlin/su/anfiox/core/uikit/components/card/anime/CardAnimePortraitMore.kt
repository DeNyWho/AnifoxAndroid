package su.anfiox.core.uikit.components.card.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import su.anfiox.core.uikit.util.DefaultPreview
import su.anifox.core.uikit.generated.resources.Res
import su.anifox.core.uikit.generated.resources.core_uikit_card_more

@Composable
fun CardAnimePortraitMore(
    modifier: Modifier = Modifier,
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .width(thumbnailWidth)
            .height(thumbnailHeight)
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(Res.string.core_uikit_card_more),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

fun LazyListScope.showCardAnimePortraitMoreWhenPastLimit(
    thumbnailHeight: Dp = CardAnimePortraitDefaults.Height.Default,
    thumbnailWidth: Dp = CardAnimePortraitDefaults.Width.Default,
    limit: Int = CardAnimePortraitDefaults.Limit.LANDSCAPE_LIMIT,
    size: Int = 0,
    onClick: () -> Unit,
) {
    if (size > limit) {
        item {
            CardAnimePortraitMore(
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
                onClick = onClick,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCardAnimePortraitMore() {
    DefaultPreview {
        CardAnimePortraitMore(
            onClick = { },
        )
    }
}