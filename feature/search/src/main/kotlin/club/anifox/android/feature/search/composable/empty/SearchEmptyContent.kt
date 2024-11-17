package club.anifox.android.feature.search.composable.empty

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.R
import club.anifox.android.feature.search.composable.history.content.SearchHistoryContent

@Composable
internal fun SearchEmptyContent(
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
) {
    val randomAnimeData = randomAnime.data.firstOrNull()

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
            if(randomAnimeData != null) {
                Text(
                    text = randomAnimeData.title,
                    maxLines = 2,
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.weight(1f))
    }
}