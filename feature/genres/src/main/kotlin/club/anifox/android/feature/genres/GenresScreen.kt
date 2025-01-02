package club.anifox.android.feature.genres

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortrait
import club.anifox.android.core.uikit.component.card.anime.CardAnimePortraitDefaults
import club.anifox.android.core.uikit.component.error.NoSearchResultsError
import club.anifox.android.core.uikit.component.grid.GridComponentDefaults
import club.anifox.android.core.uikit.component.progress.CircularProgress
import club.anifox.android.core.uikit.component.topbar.SimpleTopBarCollapse
import club.anifox.android.core.uikit.util.LocalScreenInfo
import club.anifox.android.core.uikit.util.toolbarShadow
import club.anifox.android.domain.model.anime.AnimeLight
import club.anifox.android.domain.model.common.device.ScreenType
import club.anifox.android.feature.genres.model.state.GenreUiState
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
    val searchResults = viewModel.searchResults
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(genreID, uiState.isGenresLoaded) {
        if (uiState.isGenresLoaded && uiState.genres.isNotEmpty()) {
            viewModel.initializeFilter(genreID)
        }
    }

    GenresUI(
        uiState = uiState,
        searchResults = searchResults,
        onAnimeClick = onAnimeClick,
        onBackPressed = onBackPressed,
    )
}

@Composable
private fun GenresUI(
    uiState: GenreUiState,
    onAnimeClick: (String) -> Unit,
    onBackPressed: () -> Boolean,
    searchResults: Flow<PagingData<AnimeLight>>,
) {
    val lazyGridState = rememberLazyGridState()
    val toolbarScaffoldState = rememberCollapsingToolbarScaffoldState()

    if(uiState.isLoading || uiState.isContentLoading) {
        CircularProgress()
    } else {
        CollapsingToolbarScaffold(
            modifier = Modifier.fillMaxSize(),
            state = toolbarScaffoldState,
            scrollStrategy = ScrollStrategy.EnterAlwaysCollapsed,
            toolbar = {
                SimpleTopBarCollapse(
                    title = uiState.selectedGenre.name,
                    titleStyle = MaterialTheme.typography.titleLarge,
                    titleAlign = TextAlign.Center,
                    toolbarScaffoldState = toolbarScaffoldState,
                    onBackPressed = onBackPressed,
                )
            },
            toolbarModifier = Modifier.toolbarShadow(
                shadowElevation = 4.dp,
                tonalElevation = 4.dp,
                shape = RectangleShape,
            ),
            body = {
                GenresContent(
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

    if (items.loadState.refresh is LoadState.Loading) {
        CircularProgress()
    } else {
        LazyVerticalGrid(
            modifier = GridComponentDefaults.Default.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = minColumnSize),
            state = lazyGridState,
            horizontalArrangement = CardAnimePortraitDefaults.HorizontalArrangement.Grid,
            verticalArrangement = CardAnimePortraitDefaults.VerticalArrangement.Grid,
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(CardAnimePortraitDefaults.GridItemSpan.Default))
            }

            items(
                count = items.itemCount,
                key = items.itemKey { it.url }
            ) { index ->
                val item = items[index]
                if (item != null) {
                    CardAnimePortrait(
                        modifier = Modifier
                            .width(width),
                        data = item,
                        onClick = { onAnimeClick.invoke(item.url) },
                        thumbnailHeight = height,
                        thumbnailWidth = width,
                    )
                }
            }

            items.apply {
                when (loadState.append) {
                    is LoadState.Loading -> {

                    }

                    is LoadState.Error -> {
                        item { NoSearchResultsError() }
                    }

                    is LoadState.NotLoading -> {

                    }
                }
            }
        }
    }
}