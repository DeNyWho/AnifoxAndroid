package club.anifox.android.feature.favourite

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.tab.simple.AnifoxScrollableTabRow
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLightFavourite
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.favourite.composable.empty.FavouriteEmptyContent
import club.anifox.android.feature.favourite.composable.item.AnimeFavouriteItem
import club.anifox.android.feature.favourite.composable.item.AnimeFavouriteItemDefaults
import club.anifox.android.feature.favourite.model.state.FavouriteUiState
import club.anifox.android.feature.favourite.model.tab.FavouriteTabType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
internal fun FavouriteScreen(
    viewModel: FavouriteViewModel = hiltViewModel(),
    onAnimeClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val tabs = FavouriteTabType.getAllEntries()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { tabs.size })

    // Create a map to store all LazyPagingItems
    val favouriteResults = remember {
        tabs.associateWith { tab ->
            viewModel.getFavouritesForTab(tab)
        }
    }

    // Preload current and adjacent pages
    LaunchedEffect(pagerState.currentPage) {
        val nextPageIndex = (pagerState.currentPage + 1) % tabs.size
        val nextTab = tabs[nextPageIndex]
        viewModel.prefetchFavouritesForTab(nextTab)

        val prevPageIndex = (pagerState.currentPage - 1).coerceAtLeast(0)
        val prevTab = tabs[prevPageIndex]
        viewModel.prefetchFavouritesForTab(prevTab)
    }

    FavouriteUI(
        uiState = uiState,
        pagerState = pagerState,
        tabs = tabs,
        favouriteResults = favouriteResults,
        onAnimeClick = onAnimeClick,
    )
}

@Composable
private fun FavouriteUI(
    uiState: FavouriteUiState,
    pagerState: PagerState,
    tabs: List<FavouriteTabType>,
    favouriteResults: Map<FavouriteTabType, Flow<PagingData<AnimeLightFavourite>>>,
    onAnimeClick: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Column {
                AnifoxScrollableTabRow(
                    itemModifier = Modifier.height(48.dp),
                    items = tabs,
                    selectedIndex = pagerState.currentPage,
                    onTabSelected = { index ->
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    itemToText = { tab ->
                        when (tab) {
                            is FavouriteTabType.HISTORY -> ""
                            is FavouriteTabType.FAVOURITE -> stringResource(R.string.feature_favourite_tab_favourite_title)
                            is FavouriteTabType.STATUS -> tab.status.toString()
                        }
                    },
                    selectedColor = MaterialTheme.colorScheme.primary,
                    unSelectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                Box(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    HorizontalPager(
                        state = pagerState,
                        pageSize = PageSize.Fill,
                        modifier = Modifier.fillMaxSize(),
                        beyondViewportPageCount = 1 // Keep one page on each side loaded
                    ) { page ->
                        val currentTab = tabs[page]
                        val currentFlow = favouriteResults[currentTab]

                        if (currentFlow != null) {
                            key(currentTab) {
                                val currentItems = currentFlow.collectAsLazyPagingItems()

                                val isLoading =
                                    currentItems.loadState.refresh is LoadState.Loading ||
                                            currentItems.loadState.append is LoadState.Loading

                                Box(modifier = Modifier.fillMaxSize()) {
                                    if (isLoading) {
                                        CircularProgressIndicator(
                                            modifier = Modifier.align(Alignment.Center)
                                        )
                                    } else {
                                        FavouriteContent(
                                            favouriteResults = currentItems,
                                            onAnimeClick = onAnimeClick,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FavouriteContent(
    modifier: Modifier = Modifier,
    favouriteResults: LazyPagingItems<AnimeLightFavourite>,
    onAnimeClick: (String) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    val screenInfo = LocalScreenInfo.current
    val (width, height) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                AnimeFavouriteItemDefaults.Width.Small,
                AnimeFavouriteItemDefaults.Height.Small,
            )
        }
        ScreenType.DEFAULT -> {
            Pair(
                AnimeFavouriteItemDefaults.Width.Medium,
                AnimeFavouriteItemDefaults.Height.Medium,
            )
        }
        else -> {
            Pair(
                AnimeFavouriteItemDefaults.Width.Large,
                AnimeFavouriteItemDefaults.Height.Large,
            )
        }
    }
    val minColumnSize = (screenInfo.portraitWidthDp.dp / 4).coerceAtLeast(300.dp)

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            favouriteResults.loadState.refresh is LoadState.Loading -> {
                CircularProgress()
            }
            favouriteResults.itemCount == 0 && favouriteResults.loadState.refresh is LoadState.NotLoading -> {
                FavouriteEmptyContent()
            }
        }

        LazyVerticalGrid(
            modifier = GridComponentDefaults.Default
                .fillMaxSize()
                .animateContentSize(),
            columns = GridCells.Adaptive(minSize = minColumnSize),
            state = lazyGridState,
            horizontalArrangement = AnimeFavouriteItemDefaults.HorizontalArrangement.Grid,
            verticalArrangement = AnimeFavouriteItemDefaults.VerticalArrangement.Grid,
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(0.dp))
            }

            items(
                count = favouriteResults.itemCount,
                key = favouriteResults.itemKey { it.url }
            ) { index ->
                val item = favouriteResults[index]
                if (item != null) {
                    AnimeFavouriteItem(
                        thumbnailWidth = width,
                        thumbnailHeight = height,
                        data = item,
                        onClick = onAnimeClick,
                    )
                }
            }

            if (favouriteResults.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }
        }
    }
}