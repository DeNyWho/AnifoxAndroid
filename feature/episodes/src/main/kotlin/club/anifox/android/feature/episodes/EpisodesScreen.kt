package club.anifox.android.feature.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.core.uikit.component.topbar.SimpleTopBar
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.episodes.AnimeEpisodesLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.episodes.composable.grid.item.CardEpisodeGridItem
import club.anifox.android.feature.episodes.composable.grid.item.CardEpisodeGridItemDefaults
import club.anifox.android.feature.episodes.data.EpisodesState
import kotlinx.coroutines.flow.Flow

@Composable
internal fun EpisodesScreen(
    viewModel: EpisodesViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    url: String,
    translationId: Int,
//    onEpisodeClick: (String) -> Unit,
) {
    val episodesState by viewModel.episodesState.collectAsState()
    val items = viewModel.episodesResults.collectAsLazyPagingItems()
    val loadState by viewModel.loadState.collectAsState()

    LaunchedEffect(url, translationId) {
        viewModel.initializeFilter(url, translationId)
    }

    LaunchedEffect(items.loadState) {
        viewModel.updateLoadState(items.loadState)
    }

    LaunchedEffect(loadState) {
        val currentLoadState = loadState
        if (currentLoadState?.refresh is LoadState.NotLoading) {
            viewModel.updateLoadingState(false)
        }
    }


    EpisodesUI(
        onBackPressed = onBackPressed,
        episodesState = episodesState,
        episodesResults = viewModel.episodesResults,

    )
}

@Composable
private fun EpisodesUI(
    onBackPressed: () -> Boolean,
    episodesState: EpisodesState,
    episodesResults: Flow<PagingData<AnimeEpisodesLight>>,
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
        EpisodesContent(
            modifier = Modifier
                .padding(padding),
            episodesState = episodesState,
            episodesResults = episodesResults,
        )
    }
}

@Composable
private fun EpisodesContent(
    modifier: Modifier,
    episodesState: EpisodesState,
    episodesResults: Flow<PagingData<AnimeEpisodesLight>>,
) {
    val items = episodesResults.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    val screenInfo = LocalScreenInfo.current

    val width = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            CardEpisodeGridItemDefaults.Width.GridSmall
        }
        ScreenType.DEFAULT -> {
            CardEpisodeGridItemDefaults.Width.GridMedium
        }
        else -> {
            CardEpisodeGridItemDefaults.Width.GridLarge
        }
    }
    val minColumnSize = (screenInfo.portraitWidthDp.dp / (if (screenInfo.portraitWidthDp.dp < 600.dp) 4 else 6)).coerceAtLeast(if(screenInfo.portraitWidthDp.dp < 600.dp) CardEpisodeGridItemDefaults.Width.Min else width )

    LaunchedEffect(episodesState) {
        if (!episodesState.isLoading) {
            items.refresh()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        when {
            episodesState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            items.itemCount == 0 && !episodesState.isLoading -> {
                NoSearchResultsError()
            }
            else -> {
                LazyVerticalGrid(
                    modifier = GridContentDefaults.Default.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = minColumnSize),
                    state = lazyGridState,
                    horizontalArrangement = CardEpisodeGridItemDefaults.HorizontalArrangement.Grid,
                    verticalArrangement = CardEpisodeGridItemDefaults.VerticalArrangement.Grid,
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.number }
                    ) { index ->
                        val item = items[index]
                        if (item != null) {
                            CardEpisodeGridItem (
                                modifier = Modifier.width(width),
                                data = item,
                                onClick = {  },
                            )
                        }
                    }

                    if(items.loadState.append is LoadState.Loading) {
                        /*
                            TODO Shimmer
                         */
                    }
                }
            }
        }
    }
}

//@PreviewScreenSizes
//@Composable
//private fun PreviewEpisodesUI() {
//    DefaultPreview(true) {
//        EpisodesUI()
//    }
//}
