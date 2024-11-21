package club.anifox.android.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.composable.empty.SearchEmptyContent
import club.anifox.android.feature.search.composable.item.AnimeSearchItem
import club.anifox.android.feature.search.composable.item.AnimeSearchItemDefaults
import club.anifox.android.feature.search.composable.toolbar.ContentSearchScreenToolbar
import club.anifox.android.feature.search.state.SearchState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onAnimeClick: (String) -> Unit,
) {
    val searchState by viewModel.searchState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val randomAnime by viewModel.randomAnime.collectAsState()
    val items = viewModel.searchResults.collectAsLazyPagingItems()
    val loadState by viewModel.loadState.collectAsState()

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
        onBackPressed = onBackPressed,
        searchState = searchState,
        searchResults = viewModel.searchResults,
        searchHistory = searchHistory,
        randomAnime = randomAnime,
        onQueryChange = { viewModel.search(it) },
        onAnimeClick = onAnimeClick,
        onHistoryItemClick = { query ->
            viewModel.search(query)
        },
        onDeleteHistoryClick = {
            viewModel.deleteSearchHistory()
        },
    )
}

@Composable
private fun SearchUI(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    searchState: SearchState,
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    onQueryChange: (String) -> Unit,
    onAnimeClick: (String) -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val focusRequester = remember { FocusRequester() }
    var shouldRequestFocus by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        shouldRequestFocus = true
        onDispose {
            shouldRequestFocus = false
        }
    }

    LaunchedEffect(shouldRequestFocus) {
        if (shouldRequestFocus) {
            delay(100)
            focusRequester.requestFocus()
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {
                ContentSearchScreenToolbar(
                    onBackPressed = onBackPressed,
                    searchQuery = searchState.query,
                    onSearchQueryChanged = onQueryChange,
                    focusRequest = focusRequester,
                )
            },
            body = {
                SearchContent(
                    searchState = searchState,
                    searchResults = searchResults,
                    searchHistory = searchHistory,
                    randomAnime = randomAnime,
                    lazyGridState = lazyGridState,
                    onAnimeClick = onAnimeClick,
                    onHistoryItemClick = onHistoryItemClick,
                    onDeleteHistoryClick = onDeleteHistoryClick,
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
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    lazyGridState: LazyGridState,
    onAnimeClick: (String) -> Unit,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
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

    LaunchedEffect(searchState.query) {
        if (searchState.query.isNotBlank()) {
            lazyGridState.scrollToItem(0)
        }
    }

    Box(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        when {
            (searchState.query.isEmpty() && (searchHistory.isNotEmpty() || searchState.isInitial)) -> {
                SearchEmptyContent(
                    searchHistory = searchHistory,
                    randomAnime = randomAnime,
                    onHistoryItemClick = onHistoryItemClick,
                    onDeleteHistoryClick = onDeleteHistoryClick,
                )
            }

            searchState.isLoading && !searchState.isInitial -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(48.dp)
                )
            }

            !searchState.isLoading && items.itemCount == 0 && searchState.query.isNotBlank() -> {
                NoSearchResultsError()
            }

            !searchState.isLoading && items.itemCount > 0 -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
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

                    if (items.loadState.append is LoadState.Loading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .size(32.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}