package club.anifox.android.core.uikit.component.card.video

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.R
import club.anifox.android.core.uikit.util.DefaultPreview

@Composable
fun CardVideoLandscapeMore(
    modifier: Modifier = Modifier,
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    onClick: () -> Unit,
) {
    Column {
        Card(
            modifier = modifier
                .clickable {
                    onClick.invoke()
                }
                .width(thumbnailWidth)
                .height(thumbnailHeight)
                .clip(MaterialTheme.shapes.medium),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 2.dp,
            ),
            shape = MaterialTheme.shapes.small,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.core_uikit_card_more),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

fun LazyListScope.showCardVideoLandscapeMoreWhenPastLimit(
    thumbnailHeight: Dp = CardVideoLandscapeDefaults.Height.Default,
    thumbnailWidth: Dp = CardVideoLandscapeDefaults.Width.Default,
    limit: Int = CardVideoLandscapeDefaults.Limit.LANDSCAPE_LIMIT,
    size: Int = 0,
    onClick: () -> Unit,
) {
    if (size > limit) {
        item {
            CardVideoLandscapeMore(
                thumbnailHeight = thumbnailHeight,
                thumbnailWidth = thumbnailWidth,
                onClick = onClick,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PreviewCardVideoLandscapeMore() {
    DefaultPreview {
        CardVideoLandscapeMore(
            onClick = { },
        )
    }
}