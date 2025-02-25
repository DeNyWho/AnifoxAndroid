package club.anifox.android.feature.episodes.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.ChipShimmer
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.feature.episodes.components.item.param.CardEpisodeComponentItemPreviewParam
import club.anifox.android.feature.episodes.components.item.param.CardEpisodeComponentItemProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds.Custom
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun CardEpisodeGridComponentItemShimmer(
    modifier: Modifier = Modifier,
    shimmerInstance: Shimmer = rememberShimmer(shimmerBounds = Custom),
) {
    val screenInfo = LocalScreenInfo.current
    val textHeight = 16.dp + screenInfo.fontSizePrefs.fontSizeExtra.dp

    Column(
        modifier = modifier
            .shimmer(shimmerInstance)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Card(
                modifier = Modifier
                    .weight(1f),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 2.dp,
                ),
                shape = MaterialTheme.shapes.small,
            ) {
                Box(
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        .clip(MaterialTheme.shapes.small),
                )
            }

            Column(
                modifier = Modifier
                    .weight(1.5f),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Box(
                    modifier = Modifier
                        .width(40.dp)
                        .height(18.dp)
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(textHeight)
                        .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    ChipShimmer(
                        shimmerInstance = shimmerInstance,
                    )
                    ChipShimmer(
                        shimmerInstance = shimmerInstance,
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(textHeight)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )

        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .height(textHeight)
                .background(color = MaterialTheme.colorScheme.onSurfaceVariant),
        )

        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(1.dp)
                .background(Color.LightGray.copy(0.1f))
                .fillMaxWidth(),
        )
    }
}

internal fun LazyGridScope.showCardEpisodeGridComponentItemShimmer(
    shimmerInstance: Shimmer,
    count: Int = 12,
) {
    items(count) {
        CardEpisodeGridComponentItemShimmer(
            shimmerInstance = shimmerInstance,
        )
    }
}

@Preview
@Composable
private fun PreviewCardEpisodeGridComponentItemShimmer(
    @PreviewParameter(CardEpisodeComponentItemProvider::class) param: CardEpisodeComponentItemPreviewParam,
) {
    DefaultPreview(true) {
        CardEpisodeGridComponentItemShimmer(
            modifier = param.modifier,
        )
    }
}