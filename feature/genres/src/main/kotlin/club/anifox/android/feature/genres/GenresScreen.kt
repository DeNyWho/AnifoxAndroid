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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridContentDefaults
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.anime.genre.AnimeGenre
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.domain.state.StateListWrapper
import club.anifox.android.feature.genres.composable.top.GenreTopBar
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
    onBackPressed: () -> Boolean,
) {
    val searchState by viewModel.searchState.collectAsState()
    val items = viewModel.searchResults.collectAsLazyPagingItems()
    val loadState by viewModel.loadState.collectAsState()
    val animeGenres by viewModel.animeGenres.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()

    LaunchedEffect(genreID) {
        viewModel.initializeFilter(genreID)
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

    GenresUI(
        searchState = searchState,
        searchResults = viewModel.searchResults,
        onAnimeClick = onAnimeClick,
        onBackPressed = onBackPressed,
        animeGenres = animeGenres,
        selectedGenre = selectedGenre,
    )
}

@Composable
private fun GenresUI(
    modifier: Modifier = Modifier,
    searchState: SearchState,
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
    searchResults: Flow<PagingData<AnimeLight>>,
    animeGenres: StateListWrapper<AnimeGenre>,
    selectedGenre: AnimeGenre,
) {
    val lazyGridState = rememberLazyGridState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    if(animeGenres.isLoading) {
        CircularProgress()
    } else {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {
                GenreTopBar(
                    selectedGenre = selectedGenre,
                    toolbarScaffoldState = toolbarScaffoldState,
                    onBackPressed = onBackPressed,
                )
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

    val screenInfo = LocalScreenInfo.current

    val (width, height) = when (screenInfo.screenType) {
        ScreenType.SMALL -> {
            Pair(
                CardAnimePortraitDefaults.Width.GridSmall,
                CardAnimePortraitDefaults.Height.GridSmall,
            )
        }
        ScreenType.DEFAULT -> {
            Pair(
                CardAnimePortraitDefaults.Width.GridMedium,
                CardAnimePortraitDefaults.Height.GridMedium,
            )
        }
        else -> {
            Pair(
                CardAnimePortraitDefaults.Width.GridLarge,
                CardAnimePortraitDefaults.Height.GridLarge,
            )
        }
    }

    val minColumnSize = (screenInfo.portraitWidthDp.dp / (if (screenInfo.portraitWidthDp.dp < 600.dp) 4 else 6)).coerceAtLeast(if(screenInfo.portraitWidthDp.dp < 600.dp) CardAnimePortraitDefaults.Width.Min else width )

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
                                modifier = Modifier.width(width),
                                data = item,
                                onClick = { onAnimeClick.invoke(item.url) },
                                thumbnailHeight = height,
                                thumbnailWidth = width,
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