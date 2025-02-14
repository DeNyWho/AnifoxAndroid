package club.anifox.android.feature.episodes

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.episodes.components.item.CardEpisodeGridComponentItem
import club.anifox.android.feature.episodes.components.item.CardEpisodeGridComponentItemDefaults
import club.anifox.android.feature.episodes.model.state.EpisodesUiState
import kotlinx.coroutines.flow.Flow

@Composable
internal fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    url: String,
    translationId: Int,
    onEpisodeClick: (String, Boolean?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(url, translationId) {
        viewModel.initializeFilter(url, translationId)
    }

    EpisodesUI(
        onBackPressed = onBackPressed,
        uiState = uiState,
        episodesResults = viewModel.episodesResults,
        onEpisodeClick = onEpisodeClick,
    )
}

@Composable
private fun EpisodesUI(
    onBackPressed: () -> Unit,
    uiState: EpisodesUiState,
    episodesResults: Flow<PagingData<AnimeEpisodesLight>>,
    onEpisodeClick: (String, Boolean?) -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            SimpleTopBar(
                onBackPressed = onBackPressed,
                title = stringResource(R.string.feature_episodes_top_bar_title),
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
            )
        },
    ) { padding ->
        EpisodesContentUI(
            modifier = Modifier
                .padding(padding),
            uiState = uiState,
            episodesResults = episodesResults,
            onEpisodeClick = onEpisodeClick,
        )
    }
}

@Composable
private fun EpisodesContentUI(
    modifier: Modifier,
    uiState: EpisodesUiState,
    episodesResults: Flow<PagingData<AnimeEpisodesLight>>,
    onEpisodeClick: (String, Boolean?) -> Unit,
) {
    val items = episodesResults.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    val screenInfo = LocalScreenInfo.current
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val columns = when {
        screenInfo.screenType == ScreenType.EXTRA_LARGE && isPortrait -> 2
        screenInfo.screenType == ScreenType.EXTRA_LARGE && !isPortrait -> 3
        !isPortrait -> 2
        else -> 1
    }

    if (items.loadState.refresh is LoadState.Loading) {
        CircularProgress()
    } else {
        if(items.itemCount == 0) {
            NoSearchResultsError()
        } else {
            Box(modifier = modifier.fillMaxSize()) {
                LazyVerticalGrid(
                    modifier = GridComponentDefaults.Default.fillMaxSize(),
                    columns = GridCells.Fixed(columns),
                    state = lazyGridState,
                    horizontalArrangement = CardEpisodeGridComponentItemDefaults.HorizontalArrangement.Grid,
                    verticalArrangement = CardEpisodeGridComponentItemDefaults.VerticalArrangement.Grid,
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(modifier = Modifier)
                    }

                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.number }
                    ) { index ->
                        val item = items[index]
                        if (item != null) {
                            CardEpisodeGridComponentItem(
                                currentTranslationId = uiState.translationId,
                                data = item,
                                onClick = { url ->
                                    // TODO Add a player selection dialog
                                    onEpisodeClick.invoke(url, true)
                                },
                            )
                        }
                    }

                    items.apply {
                        when (loadState.append) {
                            is LoadState.Loading -> {

                            }

                            is LoadState.Error -> {
                                item { NoSearchResultsError() }
                            }

                            is LoadState.NotLoading -> {

                            }
                        }
                    }
                }
            }
        }
    }
}
