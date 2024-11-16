package club.anifox.android.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.search.composable.item.AnimeSearchItem
import club.anifox.android.feature.search.composable.item.AnimeSearchItemDefaults
import club.anifox.android.feature.search.composable.toolbar.ContentSearchScreenToolbar
import club.anifox.android.feature.search.data.SearchState
import kotlinx.coroutines.flow.Flow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onBackPressed: () -> Boolean,
    onAnimeClick: (String) -> Unit,
) {
    val searchState by viewModel.searchState.collectAsState()
    val items = viewModel.searchResults.collectAsLazyPagingItems()
    val loadState by viewModel.loadState.collectAsState()

    BackHandler {
        onBackPressed.invoke()
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

    SearchUI(
        searchState = searchState,
        searchResults = viewModel.searchResults,
        onQueryChange = { viewModel.search(it) },
        onAnimeClick = onAnimeClick,
    )
}

@Composable
private fun SearchUI(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onQueryChange: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
) {
    val lazyGridState = rememberLazyGridState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(modifier = modifier.fillMaxSize()) {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {
                ContentSearchScreenToolbar(
                    navigateBack = { false },
                    searchQuery = searchState.query,
                    onSearchQueryChanged = onQueryChange,
                    focusRequest = focusRequester,
                )
            },
            body = {
                SearchContent(
                    searchState = searchState,
                    searchResults = searchResults,
                    lazyGridState = lazyGridState,
                    onAnimeClick = onAnimeClick,
                )
            }
        )
    }
}

@Composable
private fun SearchContent(
    modifier: Modifier = Modifier,
    searchResults: Flow<PagingData<AnimeLight>>,
    searchState: SearchState,
    lazyGridState: LazyGridState,
    onAnimeClick: (String) -> Unit,
) {
    val items = searchResults.collectAsLazyPagingItems()

    val screenInfo = LocalScreenInfo.current
    val (width, height) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                AnimeSearchItemDefaults.Width.Small,
                AnimeSearchItemDefaults.Height.Small,
            )
        }
        ScreenType.DEFAULT -> {
            Pair(
                AnimeSearchItemDefaults.Width.Medium,
                AnimeSearchItemDefaults.Height.Medium,
            )
        }
        else -> {
            Pair(
                AnimeSearchItemDefaults.Width.Large,
                AnimeSearchItemDefaults.Height.Large,
            )
        }
    }

    val minColumnSize = (screenInfo.portraitWidthDp.dp / 4).coerceAtLeast(300.dp)

    LaunchedEffect(searchState) {
        if (!searchState.isLoading) {
            items.refresh()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        when {
            searchState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            items.itemCount == 0 && !searchState.isLoading -> {
                NoSearchResultsError()
            }
            else -> {
                LazyVerticalGrid(
                    modifier = GridContentDefaults.Default.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = minColumnSize),
                    state = lazyGridState,
                    horizontalArrangement = AnimeSearchItemDefaults.HorizontalArrangement.Grid,
                    verticalArrangement = AnimeSearchItemDefaults.VerticalArrangement.Grid,
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.url }
                    ) { index ->
                        val item = items[index]
                        if (item != null) {
                            AnimeSearchItem(
                                thumbnailWidth = width,
                                thumbnailHeight = height,
                                data = item,
                                onClick = onAnimeClick,
                            )
                        }
                    }

                    if(items.loadState.append is LoadState.Loading) {
                        item { CircularProgressIndicator() }
                    }
                }
            }
        }
    }
}