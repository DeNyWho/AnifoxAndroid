package club.anifox.android.feature.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.composable.empty.SearchEmptyContent
import club.anifox.android.feature.search.composable.item.AnimeSearchItem
import club.anifox.android.feature.search.composable.item.AnimeSearchItemDefaults
import club.anifox.android.feature.search.composable.toolbar.ContentSearchScreenToolbar
import club.anifox.android.feature.search.model.state.SearchUiState
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
    val uiState by viewModel.uiState.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    val randomAnime by viewModel.randomAnime.collectAsState()

    SearchUI(
        onBackPressed = onBackPressed,
        uiState = uiState,
        searchResults = viewModel.searchResults,
        searchHistory = searchHistory,
        randomAnime = randomAnime,
        onQueryChange = { viewModel.search(it) },
        onTrailingIconClick = {
            viewModel.clearSearch()
        },
        onAnimeClick = onAnimeClick,
        onHistoryItemClick = { query ->
            viewModel.search(query)
        },
        onDeleteHistoryClick = {
            viewModel.deleteSearchHistory()
        },
        onRandomAnimeClick = { query ->
            viewModel.search(query)
        },
        onRefreshRandomAnimeClick = {
            viewModel.refreshRandomAnime()
        },
    )
}

@Composable
private fun SearchUI(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    uiState: SearchUiState,
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onAnimeClick: (String) -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
    onRandomAnimeClick: (String) -> Unit,
    onRefreshRandomAnimeClick: () -> Unit,
) {
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
                    searchQuery = uiState.query,
                    onSearchQueryChanged = onQueryChange,
                    onTrailingIconClick = onTrailingIconClick,
                    focusRequest = focusRequester,
                )
            },
            body = {
                SearchContent(
                    uiState = uiState,
                    searchResults = searchResults,
                    searchHistory = searchHistory,
                    randomAnime = randomAnime,
                    onAnimeClick = onAnimeClick,
                    onHistoryItemClick = onHistoryItemClick,
                    onDeleteHistoryClick = onDeleteHistoryClick,
                    onRandomAnimeClick = onRandomAnimeClick,
                    onRefreshRandomAnimeClick = onRefreshRandomAnimeClick,
                )
            }
        )
    }
}

@Composable
private fun SearchContent(
    modifier: Modifier = Modifier,
    searchResults: Flow<PagingData<AnimeLight>>,
    uiState: SearchUiState,
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    onAnimeClick: (String) -> Unit,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
    onRandomAnimeClick: (String) -> Unit,
    onRefreshRandomAnimeClick: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()
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

    LaunchedEffect(uiState.query) {
        if (uiState.query.isNotBlank()) {
            lazyGridState.scrollToItem(0)
        }
    }

    Box(
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        when {
            (uiState.query.isEmpty() && (searchHistory.isNotEmpty() || uiState.isInitialized)) -> {
                SearchEmptyContent(
                    searchHistory = searchHistory,
                    randomAnime = randomAnime,
                    onHistoryItemClick = onHistoryItemClick,
                    onDeleteHistoryClick = onDeleteHistoryClick,
                    onRandomAnimeClick = onRandomAnimeClick,
                    onRefreshRandomAnimeClick = onRefreshRandomAnimeClick,
                )
            }

            items.loadState.refresh is LoadState.Loading || uiState.isWaiting -> {
                CircularProgress()
            }

            items.itemCount == 0 && uiState.query.isNotBlank() -> {
                NoSearchResultsError()
            }

            items.itemCount > 0 -> {
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
                }
            }
        }
    }
}