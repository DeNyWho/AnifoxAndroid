package club.anifox.android.feature.search.composable.empty

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import club.anifox.android.core.uikit.component.icon.AnifoxIconPrimary
import club.anifox.android.core.uikit.util.clickableWithoutRipple
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.R
import club.anifox.android.feature.search.composable.history.content.SearchHistoryContent
import kotlinx.coroutines.launch

@Composable
internal fun SearchEmptyContent(
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
    onRandomAnimeClick: (String) -> Unit,
    onRefreshRandomAnimeClick: () -> Unit,
) {
    val randomAnimeData = randomAnime.data.firstOrNull()
    val rotationState = remember { Animatable(0f) }

    LaunchedEffect(randomAnime.isLoading) {
        if (randomAnime.isLoading) {
            launch {
                rotationState.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = LinearEasing
                    )
                )
                rotationState.snapTo(0f)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        if(searchHistory.isNotEmpty()) {
            SearchHistoryContent(
                searchHistory = searchHistory,
                onHistoryItemClick = onHistoryItemClick,
                onDeleteHistoryClick = onDeleteHistoryClick,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(R.string.feature_search_title_empty),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = if(randomAnimeData != null) stringResource(R.string.feature_search_title_empty_try_sub) else stringResource(R.string.feature_search_title_empty_sub),
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.titleMedium,
            )
            Box(
                modifier = Modifier.height(24.dp)
            ) {
                if(randomAnimeData != null) {
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickableWithoutRipple {
                                onRandomAnimeClick.invoke(randomAnimeData.title)
                            },
                        text = randomAnimeData.title,
                        maxLines = 1,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                    )
                }
            }
            AnifoxIconPrimary(
                modifier = Modifier
                    .size(48.dp)
                    .padding(top = 16.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickableWithoutRipple(enabled = !randomAnime.isLoading) {
                        onRefreshRandomAnimeClick.invoke()
                    }
                    .rotate(rotationState.value),
                imageVector = Filled.Refresh,
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))
    }
}