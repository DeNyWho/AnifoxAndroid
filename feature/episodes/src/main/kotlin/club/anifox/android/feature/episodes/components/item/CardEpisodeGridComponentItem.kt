package club.anifox.android.feature.episodes.components.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.chip.AnifoxChipCustom
import club.anifox.android.core.uikit.theme.amber300
import club.anifox.android.core.uikit.theme.red300
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.feature.episodes.R
import club.anifox.android.feature.episodes.components.item.param.CardEpisodeComponentItemPreviewParam
import club.anifox.android.feature.episodes.components.item.param.CardEpisodeComponentItemProvider
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import java.net.URLEncoder

@Composable
internal fun CardEpisodeGridComponentItem(
    modifier: Modifier = Modifier,
    data: AnimeEpisodesLight,
    onClick: (String) -> Unit,
    currentTranslationId: Int,
) {
    Column(
        modifier = modifier
            .clickableWithoutRipple { onClick.invoke(URLEncoder.encode("https:${data.translation.find { it.id == currentTranslationId }?.link }", "UTF-8")) }
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
                AsyncImage(
                    modifier = Modifier
                        .aspectRatio(16 / 9f)
                        .background(MaterialTheme.colorScheme.onSurfaceVariant)
                        .clip(MaterialTheme.shapes.small),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data.image)
                        .crossfade(true)
                        .size(Size.ORIGINAL)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    onError = {
                        println(it.result.throwable.message)
                    },
                )
            }

            Column(
                modifier = Modifier
                    .weight(1.5f),
            ) {
                Text(
                    text = "${stringResource(R.string.feature_episodes_episode_number_title)} ${data.number}",
                    style = MaterialTheme.typography.bodySmall,
                )

                Text(
                    text = data.title,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.align(Alignment.Start),
                )

                if (data.filler || data.recap) {
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        listOfNotNull(
                            data.filler to (R.string.feature_episodes_episode_filler_title to red300),
                            data.recap to (R.string.feature_episodes_episode_recap_title to amber300),
                        ).forEach { (condition, pair) ->
                            if (condition) {
                                val (titleRes, color) = pair
                                AnifoxChipCustom(
                                    modifierText = Modifier
                                        .padding(horizontal = 4.dp, vertical = 2.dp),
                                    title = stringResource(titleRes),
                                    containerColor = color,
                                    contentColor = Color.White,
                                    shape = MaterialTheme.shapes.small,
                                )
                            }
                        }
                    }
                }
            }
        }

        if(data.description.isNotEmpty()) {
            Text(
                text = data.description.replace("\n"," "),
                overflow = TextOverflow.Ellipsis,
                maxLines = 3,
                style = MaterialTheme.typography.bodySmall,
            )
        }

        Spacer(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(1.dp)
                .background(Color.LightGray.copy(0.1f))
                .fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun PreviewCardEpisodeGridComponentItem(
    @PreviewParameter(CardEpisodeComponentItemProvider::class) param: CardEpisodeComponentItemPreviewParam,
) {
    DefaultPreview(true) {
        CardEpisodeGridComponentItem(
            modifier = param.modifier,
            data = param.data,
            onClick = param.onClick,
            currentTranslationId = param.currentTranslationId,
        )
    }
}