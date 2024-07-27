package club.anifox.android.feature.search

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.enum.AnimeSeason
import club.anifox.android.domain.model.anime.enum.AnimeStatus
import club.anifox.android.domain.model.anime.enum.AnimeType
import club.anifox.android.feature.search.composable.item.AnimeSearchItem
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

    BackHandler {
        onBackPressed.invoke()
    }

    SearchUI(
        searchState = searchState,
        onQueryChange = { viewModel.search(it) },
        onFilterChange = { status, type, year, season ->
            viewModel.updateFilter(status, type, year, season)
        },
        onAnimeClick = onAnimeClick,
    )
}

@Composable
internal fun SearchUI(
    searchState: SearchState,
    onQueryChange: (String) -> Unit,
    onFilterChange: (AnimeStatus?, AnimeType?, Int?, AnimeSeason?) -> Unit,
    onAnimeClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val lazyColumnState = rememberLazyListState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    CollapsingToolbarScaffold(
        modifier = modifier
            .fillMaxSize(),
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
                searchResults = searchState.searchResults,
                lazyColumnState = lazyColumnState,
                onAnimeClick = onAnimeClick,
            )
        }
    )
}

@Composable
private fun SearchContent(
    searchResults: Flow<PagingData<AnimeLight>>,
    modifier: Modifier = Modifier,
    lazyColumnState: LazyListState,
    onAnimeClick: (String) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val items = searchResults.collectAsLazyPagingItems()

        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            contentPadding = WindowInsets.navigationBars.asPaddingValues(),
            state = lazyColumnState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey { it.url }
            ) { index ->
                val item = items[index]
                if (item != null) {
                    AnimeSearchItem(
                        data = item,
                        onClick = onAnimeClick,
                    )
                }
            }

            when (items.loadState.refresh) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }
                is LoadState.Error -> {
                    item {
                        Text("Error: ${(items.loadState.refresh as LoadState.Error).error.message}")
                    }
                }
                is LoadState.NotLoading -> {
                    if (items.itemCount == 0) {
                        item { Text("No results found") }
                    }
                }
            }

            if(items.loadState.append is LoadState.Loading) {
                item { CircularProgressIndicator() }
            }
        }
    }
}

//@PreviewScreenSizes
//@Composable
//private fun PreviewSearchScreenUI() {
//    AnifoxTheme {
//        Column (
//            Modifier.background(MaterialTheme.colorScheme.background)
//        ) {
//            SearchUI()
//        }
//    }
//}