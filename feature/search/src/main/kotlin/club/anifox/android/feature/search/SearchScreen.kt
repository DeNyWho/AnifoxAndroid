package club.anifox.android.feature.search

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalConfiguration
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
import club.anifox.android.core.uikit.util.KeyboardManager
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.rememberLazyGridState
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.search.component.empty.SearchEmptyComponent
import club.anifox.android.feature.search.component.item.AnimeSearchComponentItem
import club.anifox.android.feature.search.component.item.AnimeSearchComponentItemDefaults
import club.anifox.android.feature.search.component.item.showAnimeSearchComponentItemShimmer
import club.anifox.android.feature.search.component.toolbar.SearchTopBarComponent
import club.anifox.android.feature.search.model.state.SearchUiState
import club.anifox.android.feature.search.param.SearchUIPreviewParam
import club.anifox.android.feature.search.param.SearchUIProvider
import com.valentinilk.shimmer.Shimmer
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import kotlinx.coroutines.flow.Flow

@Composable
internal fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
    onAnimeClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchHistoryState by viewModel.searchHistory.collectAsState()
    val randomAnimeState by viewModel.randomAnime.collectAsState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(viewModel) {
        if (!uiState.isSearchBarFocused) {
            focusRequester.requestFocus()
            viewModel.updateSearchBarState()
        }
    }

    if (uiState.query.isNotEmpty()) {
        BackHandler {
            viewModel.clearSearch()
        }
    }

    SearchUI(
        onBackPressed = onBackPressed,
        uiState = uiState,
        searchResults = viewModel.searchResults,
        searchHistory = searchHistoryState,
        randomAnime = randomAnimeState,
        focusRequester = focusRequester,
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
    onBackPressed: () -> Unit,
    uiState: SearchUiState,
    searchHistory: List<String>,
    randomAnime: StateListWrapper<AnimeLight>,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onQueryChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    onAnimeClick: (String) -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
    onHistoryItemClick: (String) -> Unit,
    onDeleteHistoryClick: () -> Unit,
    onRandomAnimeClick: (String) -> Unit,
    onRefreshRandomAnimeClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBarComponent(
                onBackPressed = onBackPressed,
                searchQuery = uiState.query,
                onSearchQueryChanged = onQueryChange,
                onTrailingIconClick = onTrailingIconClick,
                focusRequester = focusRequester,
            )
        },
        content = { padding ->
            SearchContent(
                modifier = Modifier.padding(padding),
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
    val items = searchResults.collectAsLazyPagingItems()
    val gridState = items.rememberLazyGridState()

    val screenInfo = LocalScreenInfo.current
    val configuration = LocalConfiguration.current

    val isPortrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val columns = when {
        screenInfo.screenType == ScreenType.EXTRA_LARGE && isPortrait -> 2
        screenInfo.screenType == ScreenType.EXTRA_LARGE && !isPortrait -> 3
        !isPortrait -> 2
        else -> 1
    }

    val (thumbnailWidth, thumbnailHeight) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                AnimeSearchComponentItemDefaults.Width.Small,
                AnimeSearchComponentItemDefaults.Height.Small,
            )
        }

        ScreenType.DEFAULT -> {
            Pair(
                AnimeSearchComponentItemDefaults.Width.Medium,
                AnimeSearchComponentItemDefaults.Height.Medium,
            )
        }

        else -> {
            Pair(
                AnimeSearchComponentItemDefaults.Width.Large,
                AnimeSearchComponentItemDefaults.Height.Large,
            )
        }
    }

    Box(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxSize(),
    ) {
        when {
            (uiState.query.isEmpty()) -> {
                SearchEmptyComponent(
                    searchHistory = searchHistory,
                    randomAnime = randomAnime,
                    onHistoryItemClick = onHistoryItemClick,
                    onDeleteHistoryClick = onDeleteHistoryClick,
                    onRandomAnimeClick = onRandomAnimeClick,
                    onRefreshRandomAnimeClick = onRefreshRandomAnimeClick,
                )
            }

            items.loadState.append.endOfPaginationReached && items.itemCount == 0 -> {
                NoSearchResultsError()
            }

            else -> {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(columns),
                    state = gridState,
                    horizontalArrangement = AnimeSearchComponentItemDefaults.HorizontalArrangement.Grid,
                    verticalArrangement = AnimeSearchComponentItemDefaults.VerticalArrangement.Grid,
                ) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Spacer(modifier = Modifier)
                    }

                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.url },
                    ) { index ->
                        val item = items[index]
                        item?.let {
                            AnimeSearchComponentItem(
                                thumbnailWidth = thumbnailWidth,
                                thumbnailHeight = thumbnailHeight,
                                data = item,
                                onClick = onAnimeClick,
                            )
                        }
                    }

                    when {
                        items.loadState.append is LoadState.Loading -> {
                            showAnimeSearchComponentItemShimmer(
                                shimmerInstance = shimmer,
                                thumbnailHeight = thumbnailHeight,
                                thumbnailWidth = thumbnailWidth,
                            )
                        }

                        items.loadState.refresh is LoadState.Loading -> {
                            showAnimeSearchComponentItemShimmer(
                                shimmerInstance = shimmer,
                                thumbnailHeight = thumbnailHeight,
                                thumbnailWidth = thumbnailWidth,
                            )
                        }

                        items.loadState.append is LoadState.Error -> {
                        }
                    }
                }

                KeyboardManager(gridState)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewSearchUI(
    @PreviewParameter(SearchUIProvider::class) param: SearchUIPreviewParam,
) {
    DefaultPreview(true) {
        SearchUI(
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