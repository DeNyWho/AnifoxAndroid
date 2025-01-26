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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.util.DefaultPreview
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.composable.empty.SearchEmptyContent
import club.anifox.android.feature.search.composable.item.AnimeSearchItem
import club.anifox.android.feature.search.composable.item.AnimeSearchItemDefaults
import club.anifox.android.feature.search.composable.item.showAnimeSearchItemShimmer
import club.anifox.android.feature.search.composable.toolbar.ContentSearchScreenToolbar
import club.anifox.android.feature.search.model.state.SearchUiState
import club.anifox.android.feature.search.param.SearchUiPreviewParam
import club.anifox.android.feature.search.param.SearchUiProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
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
    shimmer: Shimmer = rememberShimmer(ShimmerBounds.View),
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
    val (thumbnailWidth, thumbnailHeight) = when (screenInfo.screenType) {
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
            .padding(horizontal = 16.dp)
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

            items.itemCount == 0 && uiState.query.isNotBlank() -> {
                NoSearchResultsError()
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Adaptive(minSize = minColumnSize),
                    state = lazyGridState,
                    horizontalArrangement = AnimeSearchItemDefaults.HorizontalArrangement.Grid,
                    verticalArrangement = AnimeSearchItemDefaults.VerticalArrangement.Grid,
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.url },
                    ) { index ->
                        val item = items[index]
                        item?.let {
                            AnimeSearchItem(
                                thumbnailWidth = thumbnailWidth,
                                thumbnailHeight = thumbnailHeight,
                                data = item,
                                onClick = onAnimeClick,
                            )
                        }
                    }

                    when {
                        items.loadState.append is LoadState.Loading -> {
                            showAnimeSearchItemShimmer(
                                shimmerInstance = shimmer,
                                thumbnailHeight = thumbnailHeight,
                                thumbnailWidth = thumbnailWidth,
                            )
                        }

                        items.loadState.refresh is LoadState.Loading -> {
                            showAnimeSearchItemShimmer(
                                shimmerInstance = shimmer,
                                thumbnailHeight = thumbnailHeight,
                                thumbnailWidth = thumbnailWidth,
                            )
                        }

                        items.loadState.append is LoadState.Error -> {

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSearchScreenUI(
    @PreviewParameter(SearchUiProvider::class) param: SearchUiPreviewParam,
) {
    DefaultPreview(true) {
        SearchUI(
            modifier = param.modifier,
            onBackPressed = param.onBackPressed,
            uiState = param.uiState,
            searchHistory = param.searchHistory,
            randomAnime = param.randomAnime,
            onQueryChange = param.onQueryChange,
            onTrailingIconClick = param.onTrailingIconClick,
            onAnimeClick = param.onAnimeClick,
            searchResults = param.searchResults,
            onHistoryItemClick = param.onHistoryItemClick,
            onDeleteHistoryClick = param.onDeleteHistoryClick,
            onRandomAnimeClick = param.onRandomAnimeClick,
            onRefreshRandomAnimeClick = param.onRefreshRandomAnimeClick,
        )
    }
}