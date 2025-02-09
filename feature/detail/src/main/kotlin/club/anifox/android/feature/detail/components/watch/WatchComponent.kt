package club.anifox.android.feature.detail.components.watch

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.button.AnifoxButtonPrimary
import club.anifox.android.core.uikit.component.icon.AnifoxIconOnPrimary
import club.anifox.android.domain.model.anime.AnimeDetail
import club.anifox.android.domain.state.StateWrapper
import club.anifox.android.feature.detail.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun WatchComponent(
    modifier: Modifier = Modifier,
    detailAnimeState: StateWrapper<AnimeDetail>,
    onWatchClick: () -> Unit,
) {
    if (!detailAnimeState.isLoading && detailAnimeState.data != null) {
        val primaryColor = MaterialTheme.colorScheme.primary

        Box(
            modifier = modifier
                .fillMaxWidth()
                .then(
                    if (detailAnimeState.data?.nextEpisode != null) {
                        Modifier.padding(bottom = 16.dp)
                    } else {
                        Modifier
                    }
                )
        ) {
            if(detailAnimeState.data?.nextEpisode != null) {
                val nextEpisodeText = when {
                    LocalDateTime.now().isAfter(detailAnimeState.data?.nextEpisode) -> stringResource(R.string.feature_detail_section_watch_next_episode_title_delayed)
                    else -> "${stringResource(R.string.feature_detail_section_watch_next_episode_title)} ${
                        detailAnimeState.data?.nextEpisode?.format(
                            DateTimeFormatter.ofPattern("dd.MM.yyyy")
                        )
                    }"
                }

                Box(
                    modifier = Modifier
                        .offset(y = 20.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(45.dp)
                            .align(Alignment.Center)
                    ) {
                        drawRoundRect(
                            color = primaryColor.copy(alpha = 0.2f),
                            cornerRadius = CornerRadius(16.dp.toPx(), 16.dp.toPx())
                        )
                    }

                    Text(
                        modifier = Modifier
                            .align(Alignment.BottomCenter),
                        text = nextEpisodeText,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }

            AnifoxButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp
                ),
                paddingValues = PaddingValues(0.dp),
                onClick = {
                    onWatchClick.invoke()
                },
            ) {
                AnifoxIconOnPrimary(
                    imageVector = Filled.PlayArrow,
                    contentDescription = stringResource(R.string.feature_detail_section_description_button_watch),
                    modifier = Modifier.size(40.dp),
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(R.string.feature_detail_section_watch_button_watch_title),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}