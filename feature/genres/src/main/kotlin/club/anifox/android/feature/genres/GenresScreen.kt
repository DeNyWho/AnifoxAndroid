package club.anifox.android.feature.genres

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.genres.data.SearchState
import kotlinx.coroutines.flow.Flow
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun GenresScreen(
    viewModel: GenresViewModel = hiltViewModel(),
    genreID: String,
    onAnimeClick: (String) -> Unit,
) {
    val searchState by viewModel.searchState.collectAsState()
    val items = viewModel.searchResults.collectAsLazyPagingItems()
    val loadState by viewModel.loadState.collectAsState()
    val animeGenres by viewModel.animeGenres.collectAsState()

    LaunchedEffect(genreID) {
        viewModel.initializeFilter(genreID)
    }

    LaunchedEffect(items.loadState) {
        viewModel.updateLoadState(items.loadState)
    }

    LaunchedEffect(loadState) {
        val currentLoadState = loadState
        when {
            currentLoadState?.refresh is LoadState.NotLoading -> viewModel.updateLoadingState(false)
            currentLoadState?.refresh is LoadState.Error -> viewModel.updateLoadingState(false)
            currentLoadState?.refresh is LoadState.Loading -> viewModel.updateLoadingState(true)
        }
    }

    GenresUI(
        searchState = searchState,
        searchResults = viewModel.searchResults,
        onAnimeClick = onAnimeClick,
        animeGenres = animeGenres,
    )
}

@Composable
private fun GenresUI(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onAnimeClick: (String) -> Unit,
    searchResults: Flow<PagingData<AnimeLight>>,
    animeGenres: StateListWrapper<AnimeGenre>,
) {
    val lazyGridState = rememberLazyGridState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    Box(modifier = modifier.fillMaxSize()) {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {

            },
            body = {
                GenresContent(
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
private fun GenresContent(
    modifier: Modifier = Modifier,
    searchResults: Flow<PagingData<AnimeLight>>,
    searchState: SearchState,
    lazyGridState: LazyGridState,
    onAnimeClick: (String) -> Unit,
) {
    val items = searchResults.collectAsLazyPagingItems()

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
                    columns = GridCells.Adaptive(minSize = CardAnimePortraitDefaults.Width.Grid),
                    state = lazyGridState,
                    horizontalArrangement = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
                    verticalArrangement = CardAnimePortraitDefaults.VerticalArrangement.Grid,
                ) {
                    items(
                        count = items.itemCount,
                        key = items.itemKey { it.url }
                    ) { index ->
                        val item = items[index]
                        if (item != null) {
                            CardAnimePortrait(
                                modifier = Modifier.width(CardAnimePortraitDefaults.Width.Grid),
                                data = item,
                                onClick = { onAnimeClick.invoke(item.url) },
                                thumbnailHeight = CardAnimePortraitDefaults.Height.Grid,
                                thumbnailWidth = CardAnimePortraitDefaults.Width.Grid,
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

//@PreviewScreenSizes
//@Composable
//private fun PreviewGenresUI() {
//    AnifoxTheme {
//        Column (
//            Modifier.background(MaterialTheme.colorScheme.background)
//        ) {
//            GenresUI()
//        }
//    }
//}
